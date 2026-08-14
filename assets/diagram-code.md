## Eraser Logical Flow Diagram Code
---
```
direction down

title url2Product Logical Flow

Request [shape: oval, icon: send, color: blue]
Response [shape: oval, icon: send, color: blue]

API Layer [color: blue] {
  Extract Product Request [icon: file-text]
  Validate URL [icon: check]
  Valid URL [shape: diamond, icon: help-circle]
  Bad Request [shape: oval, icon: x, color: red]
}

Web Processing [color: orange] {
  Fetch Webpage [icon: globe]
  Clean and Extract Content [icon: filter]
  Prepared Content [shape: oval, icon: file-text]
}

AI Extraction [color: purple] {
  Spring AI OpenAI [icon: sparkles]
  Extract Product Details [icon: package]
  Product Details [shape: oval, icon: package]
}

Currency Processing [color: green] {
  Currency Conversion Required [shape: diamond, icon: dollar-sign]
  Frankfurter [shape: oval, icon: refresh-cw]
  USD Price [shape: oval, icon: dollar-sign]
}


Response Processing [color: blue] {
  Evaluate Extraction Status [icon: check-circle]
  Extraction Status [shape: oval, icon: activity]
}

Request > Extract Product Request
Extract Product Request > Validate URL
Validate URL > Valid URL

Valid URL > Bad Request: No
Valid URL > Fetch Webpage: Yes

Fetch Webpage > Clean and Extract Content
Clean and Extract Content > Prepared Content

Prepared Content --> Spring AI OpenAI
Spring AI OpenAI > Extract Product Details
Extract Product Details > Product Details

Product Details > Currency Conversion Required

Currency Conversion Required --> Frankfurter: Yes
Currency Conversion Required > Evaluate Extraction Status: No

Frankfurter > USD Price
USD Price > Evaluate Extraction Status

Evaluate Extraction Status > Extraction Status
Extraction Status > Response

legend

{
  [connection: >, label: internal application flow]
  [connection: -->, label: external service call]
  [shape: rectangle, label: processing]
  [shape: diamond, label: decision]
  [shape: oval, label: result]
  [shape: oval, color: red, label: error]
}
```
