# Kiara DB: Beginner-Friendly Guide + Detailed Feature Reference

This README explains how to use Kiara DB from scratch, then walks through its advanced capabilities.

Kiara DB is designed for fast in-memory search across many combinations of fields without building composite indexes.

---

## Table of Contents

1. What Kiara DB Is
2. When to Use It (and When Not To)
3. Quick Start (10 Minutes)
4. Core Concepts
5. Data Modeling
6. Loading Data
7. Querying Data
8. Type-Ahead / Text Prediction
9. Runtime Hierarchical Config Rules
10. Performance Characteristics
11. Memory Optimization Techniques
12. Scaling Approach
13. Troubleshooting and Practical Notes
14. Useful References

---

## 1) What Kiara DB Is

Kiara DB is a Java in-memory, read-only NoSQL data store built around trie-based indexing.

### Key idea
- You can query by many combinations of fields without manually creating composite indexes.
- Query performance is designed to be stable with respect to total database size, and more dependent on:
  - Number of search conditions
  - Number of records returned

### Core tradeoff
- Kiara operates on static/read-mostly data.
- If data changes, the common pattern is to reload the in-memory store.

---

## 2) When to Use It (and When Not To)

### Great fit
- Read-heavy, low-mutation workloads
- Fast filtering/search over many key combinations
- In-memory services where response time matters
- Type-ahead / keyword suggestion use cases
- Rule/config engines where best or minimum match is needed

### Not ideal
- High-frequency transactional writes
- Workloads requiring built-in durable persistence
- Strongly relational use cases needing joins between entities

---

## 3) Quick Start (10 Minutes)

## 3.1 Prerequisites
- Java project (Maven shown below)

## 3.2 Add dependency

```xml
<dependency>
  <groupId>com.kapoorlabs</groupId>
  <artifactId>kiara</artifactId>
  <version>2.0.10</version>
</dependency>
```

Notes:
- Documentation examples also show versions like 2.0.6 and 2.0.9.
- Prefer the latest stable available in Maven Central for your project.

## 3.3 Define a POJO schema

```java
public class Airport {
  private String country;
  private String state;
  private String city;
  private String airportCode;
}
```

## 3.4 Create store + load records

```java
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.loader.StoreLoader;

Store<Airport> airportStore = new Store<>(Airport.class);
StoreLoader<Airport> storeLoader = new StoreLoader<>(airportStore);

for (Airport record : records) {
  storeLoader.loadTable(record);
}

storeLoader.prepareForSearch();
```

## 3.5 Run your first query

```java
import com.kapoorlabs.kiara.domain.Condition;
import com.kapoorlabs.kiara.search.StoreSearch;

StoreSearch storeSearch = new StoreSearch();

List<Condition> conditions = new LinkedList<>();
conditions.add(new Condition("country", Operator.EQUAL, "US"));
conditions.add(new Condition("state", Operator.EQUAL, "FL"));

List<Airport> result = storeSearch.query(airportStore, conditions);
```

You now have a working in-memory searchable store.

---

## 4) Core Concepts

### Store
- `Store<T>` is the in-memory dataset for a POJO schema.

### Loader
- `StoreLoader<T>` ingests records into the store.
- Call `prepareForSearch()` once loading is complete.

### Search
- `StoreSearch` executes declarative conditions.

### Data model style
- Non-relational data-store collections (POJO-based)
- Design favors denormalized, query-optimized structures

---

## 5) Data Modeling

Kiara supports simple and annotation-driven complex field representations.

## 5.1 Simple data types
- Primitives: `byte`, `short`, `char`, `int`, `long`, `float`, `double`
- Boxed: `Byte`, `Short`, `Character`, `Integer`, `Long`, `Float`, `Double`
- `String`, `boolean`

## 5.2 Complex types via annotations (string-backed)

### Date/time
- `@DateFormat`
- `@DateTimeFormat`

### Ranges
- `@NumericRange`
- `@DateRange`
- `@DateTimeRange`

### Lists
- `@CommaSeperatedStrings`
- `@CommaSeperatedNumbers`
- `@CommaSeperatedDates`
- `@CommaSeperatedDateTimes`
- `@CommaSeperatedDateRanges`
- `@CommaSeperatedDateTimeRanges`
- `@CommaSeperatedNumericRanges`

### Search-assist annotations
- `@Predictable` (include field in type-ahead dictionary)
- `@CaseInsensitive` (shown in examples/imports; use based on your matching needs)

Example:

```java
@DateFormat(value = "MM/dd/yy")
private String bookingDate;

@NumericRange
private String seatRange;

@CommaSeperatedStrings
private String tags;
```

---

## 6) Loading Data

Recommended loading workflow:

1. Instantiate `Store<T>`
2. Instantiate `StoreLoader<T>`
3. Call `loadTable(record)` for each input row
4. Call `prepareForSearch()`

If data changes at runtime, rebuild/reload the store from source-of-truth data.

---

## 7) Querying Data

## 7.1 Condition shape
Each condition uses:
- Field name
- Operator
- Value (or list/range depending on operator)

```java
new Condition("city", Operator.EQUAL, "NYC");
```

## 7.2 Result styles

### A) Full POJO result
```java
List<Airport> result = storeSearch.query(airportStore, conditions);
```

### B) Selected fields only
```java
Set<String> filterSet = new HashSet<>();
filterSet.add("airport_name");
filterSet.add("city_name");

List<Map<String, String>> result = storeSearch.query(airportStore, conditions, filterSet);
```

Use selected fields when returning large result sets to reduce payload and parsing overhead.

## 7.3 Common operators
- `EQUAL`
- `NOT_EQUAL`
- `LESS_THAN`
- `LESS_THAN_EQUAL`
- `GREATER_THAN`
- `GREATER_THAN_EQUAL`
- `BETWEEN`
- `CONTAINS_EITHER` (for list-like fields)
- `CONTAINS_ALL` (for list-like fields)

Examples:

```java
conditions.add(new Condition("state", Operator.EQUAL, new String[]{"FL", "NY", "MA"}));
conditions.add(new Condition("age", Operator.BETWEEN, 21, 30));
conditions.add(new Condition("updatedDate", Operator.EQUAL, LocalDate.now().minusDays(1)));
conditions.add(new Condition("authors", Operator.CONTAINS_EITHER, "Author A,Author B"));
```

---

## 8) Type-Ahead / Text Prediction

Kiara supports text prediction by building a prediction trie from fields marked `@Predictable`.

Example model:

```java
@Data
public class Airport {
  @Predictable private String airportCode;
  @Predictable private String airportName;
  @Predictable private String countryName;
}
```

Prediction call pattern:

```java
List<String> predictions = SpellCheckUtil.getTextPredictions(
    searchString,
    airportStore.getAirportStore().getSpellCheckTrie(),
    topNMatches
);
```

This is suitable for instant search bars and auto-complete UX.

---

## 9) Runtime Hierarchical Config Rules

A practical pattern shown in the docs is hierarchical config matching using keyword/minimum match behavior.

Use case example:
- Apply surcharges by specificity levels such as:
  - country
  - city
  - airline
- More specific rule overrides broader rule.

Illustrative flow:

```java
KeywordSearchResult<Config> result = keywordSearch.getMinimumMatch(
    country + " " + city + " " + airline,
    configStore
);
Double surcharge = result.getResult().get(0).getSurCharge();
```

---

## 10) Performance Characteristics

From benchmark notes in the provided docs:
- Response time is not primarily tied to total row count.
- It scales more with:
  - Number of conditions in query
  - Number of returned records
- Returned row count vs response time is shown as roughly linear in examples.

Documented benchmark environment:
- MacBook Pro
- 2.8 GHz Quad-Core Intel Core i7
- 16 GB RAM

Treat these numbers as directional and benchmark in your own environment.

---

## 11) Memory Optimization Techniques

## 11.1 Implicit compression (trie dedup)
- Shared prefixes are deduplicated naturally in trie structure.
- Design tip: place high-cardinality/unique keys later in POJO field order to improve early-level dedup benefits.

## 11.2 Explicit compression patterns
- Use list fields instead of repeating near-identical records.
- Use ranges for contiguous values.
- Use list-of-ranges for multiple intervals.

These patterns can reduce heap footprint significantly in denormalized datasets.

---

## 12) Scaling Approach

Kiara docs describe horizontal partitioning patterns such as:
- Feature-based partitions (store-level split)
- Key-based partitions (partition key-based routing)
- Directory/custom-rule partitions

Design your partitioning around access patterns so each request touches as few nodes as possible.

---

## 13) Troubleshooting and Practical Notes

### Data updates
- Kiara is intended for static/read-mostly data.
- Common update strategy is store rebuild/reload.

### Null handling and edge semantics
- Validate behavior for nulls/range boundaries in your own tests.

### Java compatibility and threading details
- Docs confirm Java integration but do not clearly state minimum Java version and full concurrency guarantees.
- Validate in your runtime profile (load tests + integration tests).

### Persistence
- Built-in durable storage is not the primary model; treat Kiara stores as in-memory acceleration/search layer.

---

## 14) Useful References

### Local documentation in this workspace
- `extracted/Kiara -Deep Dive - Kapoor-Labs.txt`
- `extracted/Kiara DB - Kapoor-Labs.txt`
- `extracted/Kiara - Demo Project - Kapoor-Labs.txt`
- `extracted/Performance - Kapoor-Labs.txt`
- `extracted/Make a type ahead engine with Kiara in 5 minutes! - Kapoor-Labs.txt`
- `extracted/Make a runtime hierarchical config engine with Kiara in 5 minutes! - Kapoor-Labs.txt`

### Demo repository
- https://github.com/kapoorlabs/kiara-demo

---

## Suggested First Learning Path

1. Run the Quick Start in this README.
2. Build one simple filter query (`EQUAL`, `BETWEEN`).
3. Add one complex field type (date/range/list).
4. Add `@Predictable` and test type-ahead.
5. Implement one rule engine pattern (`getMinimumMatch`) if needed.

By then, you will have covered both everyday usage and advanced capabilities.
