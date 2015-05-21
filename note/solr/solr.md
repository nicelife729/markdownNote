Solr分面机制
===
----
### 分面的概念：
> Solr有一个高级特性是可以对常规的基本搜索结果进行聚合。Solr的分类机制，也就是分面(faceing)，提供了我们日常应用中所需要的一些功能，包括从文档中获取同一字段重复值，例如同一个城市的不同公司，使用了日期和范围的分面功能，可以基于这个实现自动完成(autocomplete)。

### 分面功能的使用：

#### 统计中重复值的数量

场景：有一个应用允许用户搜索某一区域的公司，但客户端需要统计该区域每个子区域的公司数量，这是就用到了这个
使用：在查询是指定_facet_ 和 _facet.field_
```
http://localhost:8983/solr/select?q=name:company&facet=true&facet.field=city
```

#### 统计某一范围
场景：对商品进行价格分段统计
```
http://localhost:8983/solr/select?q=*:*&rows=0&facet=true&facet.
range=price&facet.range.start=0&facet.range.end=400&facet.range.
gap=100
```

#### 统计子查询
场景：有一个搜索汽车特性的APP，有一个需求是不仅显示搜索结果，并且显示用户指定价格范围的汽车的数量
```
http://localhost:8983/solr/select?q=name:car&facet=true&facet.
query=price:[10 TO 80]&facet.query=price:[90 TO 300]
```

#### 从分面搜索结果中移除过滤器
场景：有一个应用可以搜索在一个城市和州里面的公司。但是需求是不仅展示搜索结果，而且还要每个城市公司的数量和每个周里城市的数量。
```
http://localhost:8983/solr/select?q=name:company&facet=true 
&fq={!tag=stateTag}state:"New York"&facet.field={!ex=stateTag}
city&facet.field={!ex=stateTag}state
```
说明：
`fq={!tag=stateTag}state:"NewYork"`仅显示包含state为NewYork的结果。
`{!tag=stateTag}` 由于受搜索结果的影响，不可能在结果中统计出该state中公司的数量，因此需要从分面搜索结果中排除，仅需在每个facet.field后添加`{!tag=stateTag}`

#### 按拼音排序分面结果
```
http://localhost:8983/solr/select?q=name:house&facet=true&facet.
field=city&facet.sort=index
```
说明：facet.sort=index 是按字母排序，facet.sort=count是按统计量排序

#### 使用分面实现自动建议的特性
场景：有很多web应用帮助用户推荐搜索什么，其中一个特性绝大多数使用的自动完成，或者是自动建议。
步骤：
1. 在schema.xml中定义自动完成的字段
```
<field name="id" type="string" indexed="true" stored="true" 
required="true" />
<field name="title" type="text" indexed="true" stored="true" />
<field name="title_autocomplete" type="lowercase" indexed="true" stored="true">
```
2. 在schema.xml中添加复制字段
```
<copyField source="title" dest="title_autocomplete" />
```
3. 定义filedType
```
<fieldType name="lowercase" class="solr.TextField">
    <analyzer>
        <tokenizer class="solr.KeywordTokenizerFactory"/>
        <filter class="solr.LowerCaseFilterFactory" />
    </analyzer>
</fieldType>
```
4. 添加数据
5. 查询
```
http://localhost:8983/solr/select?q=*:*&rows=0&facet=true&facet.
field=title_autocomplete&facet.prefix=so
```
说明：facet.prfix使用户的部分输入

#### 统计不存在的字段

```
http://localhost:8983/solr/select?q=title:solr&facet=true&facet.
query=!price:[* TO *]
```
说明：统计不存在price值的数量

#### 在一个查询中实现两个分面操作
场景：在应用中有一个汽车的数据库，除了标准的检索，还需要获取两个不同的字段的分面结果，其中一条是各分类的汽车的数量（不限制数量），还有一个是汽车的生产厂商，并且限制最多10个结果。这么复杂的查询可以在一条solr查询中实现吗？
```
http://localhost:8983/solr/select?q=name:car&facet=true&facet.
field=category&facet.field=manufacturer&f.category.facet.limit=-1&f.manufacturer.facet.limit=10
```
说明：使用&连接多个facet.field，后面跟上的限制需要指定field
`f.category.facet.limit=-1`取消结果数量限制

#### 使用决策树分面
场景：假设我们的商店有很多种类的商品，并且存储了每种商品的库存信息。现在，我们想要向工作人员显示该产品在类别有库存，有多少没有库存。首先先到的是使用分面机制和一些额外的计算。但是从Solr4.0开始可以使用pivot faceting 实现的更好。
```
http://localhost:8983/solr/select?q=*:*&rows=0&facet=true&facet.pivot=category,stock
```
结果类似于：
```
<lst name="facet_pivot">
    <arr name="category,stock">
        <lst>
        <str name="field">category</str>
        <str name="value">books</str>
        <int name="count">2</int>
        <arr name="pivot">
            <lst>
                <str name="field">stock</str>
                <bool name="value">true</bool>
                <int name="count">2</int>
            </lst>
        </arr>
        </lst>
    </arr>
</lst>
```
说明：这个还要再看看

#### 集合(group)中相关文档的分面统计
场景：如果使用过solr的field collapsing的功能，你会想是否可以将这个功能和分面结合使用。当然是可以的，但在solr中默认只能对基本的文档进行分面相关的计算，多文档组成的group就不行了。
```
http://localhost:8983/solr/select?q=*:*&facet=true&facet.
field=stock&group=true&group.field=category&group.truncate=true
```

```
#计算结果如下
{
groupValue: "books",
doclist: {
    numFound: 2,
    start: 0,
    docs: [
        {
            id: "1",
            name: "Book 1",
            category: "books",
            stock: true,
            _version_: 1469416908335874000
        }
    ]
    }
}
...
facet_fields: {
    stock: [
        "true",
        3,
        "false",
        1
    ]
}
```
说明：
使用group.truncate=true会将重复的项当成一个计算
group.truncate在分布式搜索中不支持


> Written with [StackEdit](https://stackedit.io/).