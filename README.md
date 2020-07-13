# Kiara DB <img src="https://www.kapoorlabs.com/wp-content/uploads/2020/06/54624690-AA61-4E27-8305-9234C6861A86-e1593536390892.png" width="10%" align="center" />
> A powerful in-memory static database that lets you search by any combination of search keys, at high performance, without the need of indexes.<br><br>
Born at Priceline.com
 


#### Inspiration and Reasons behind building a new database: -

Kiara DB was born at Priceline.com while dealing with a complex problem of calculating flight commissions in good response times!

For calculating a commission, there were about 100 variables, or you could that there were 100 columns in the database that stores thousands of rules that could apply to an itinerary.
When we try to match an itinerary with a particular rule in the database, we may match all 100 or just a subset, say 90 columns, it really depends on the itinerary. For another itin we may match another subset, say 80 columns. and so on..
So you see, we had to search the database using various possible combinations of columns, and that decision was made at runtime, while processing itins. Also keep in mind that the search criteria was not simply "equality". We had to deal with some complex data types like ranges (AA1-100), comma separated values(ECO,BEC,PEC) and support various operations like equality (==),  inequality(!=), relational (<, > ,<=, >=), List matching(partial match,  complete match), Range match (whether AA12 satisfies range in database column AA1-100).

So, the only way our search queries would have been performant, was if we had an index, for every possible search query that could be generated on runtime.
In all available databases we have 2 fundamental types of indexes, Hash indexes and indexes based on B-Tree.

If we consider just 20 columns which may vary(that may or may not be present in the search criteria), 
the number of all possible hash indexes required would be  2^20 = 2 Million.
And then if we consider databases with  indexes like B-Tree, the number of indexes required were (1 + (n(n-1))/2)) = 190

Even though the number with B-tree is way better than a hash index, the number is still big, considering indexes are expensive and it might grow in future with additional columns.

And then, after some research Kiara DB was born, which is a **no multi-key index database**, that would give **consistent good performance with any combination of keys**, in addition to good developer experience.
 
 
#### Research Paper: -

If you are interested in diving deep into the concepts, please feel free to read the research paper on Kiara DB which was published in the Journal of Algorithms and Computation by clicking below:- <br> <br>
[Read Research Paper](https://jac.ut.ac.ir/article_76227_f4783d3e9e2ddaf29259318978af6743.pdf) <br> <br>
Published in Journal of Algorithms and Computation - June 2020

#### Details on how to get started with Kiara: -

##### Introduction:- 
[Click here for an Introduction.](https://www.kapoorlabs.com/kiara/)

##### Quick start guide:- 
[Click here for Quick Start Guide.](https://www.kapoorlabs.com/kiara-howto/)


##### Quick start guide:- 
[Click here for Deep Dive Guide.](https://www.kapoorlabs.com/kiara-deep-dive/)


##### Performance metrics:- 
[Click here for Performance Metrics.](https://www.kapoorlabs.com/kiara-performance/)

##### Jump start Demo Project:- 
[Click here for Jump start Demo Project.](https://www.kapoorlabs.com/kiara-demo-project)


##### Video walkthroughs: -

[How Kiara stands out](https://www.youtube.com/watch?time_continue=1&v=-AL6TrOgdKI&feature=emb_logo) <br> <br>
[Sample query walkthrough](https://www.youtube.com/watch?time_continue=2&v=-T-Bwu0WQAI&feature=emb_logo)


