 # Kiara DB <img src="http://box5885.temp.domains/~kapoorla/wp-content/uploads/2020/03/54624690-AA61-4E27-8305-9234C6861A86.png" width="10%" align="center" />  

**Family of, powerful in-memory static databases is here!**

Kiara DB is a NoSql Trie based in-memory database, especially engineered for data that doesn't change very often, or in other words, it focuses on read only operations, to make reads most efficient.
 It provides a very *efficient* storage and a very flexible query interface, but the most distinguishable feature is that: - 
 

> You can query by any column or any permutation/combination of columns at consistent high performance without having to create a single composite index. So you might as well generate queries at runtime, without having to worry about performance

There is no existing database that provides this feature. Databases like Redis and Sql Lite are based on Hash indexes and B*-Tree indexes, which can provide very limited searching capabilities. In fact it is impossible. In fact, in hash and B*-Tree index based databases, if you need to have the freedom for searching by any combination of columns, the required number of indexes required will increase exponentially, precisely being (2^n)/n. 
If n(number of columns) even gets as big as 15 or 20, we are talking about million indexes, thus its an impossible task for other databases (in this case Kiara will only create 20 indexes, as opposed to 1 million)

Plus there are many other power unique features like memory de-duplication, range based searching,  partial or full collection search, partial exclusions which are discussed further.

Currently, kiara only supports Java.

Documentation is expected to be released by May 2020.
