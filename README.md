# Test application
Start application and try to do some calculations using url:  
GET http://localhost:8080/calc?method={method}&numbers={numbers_split_by_comma}  

Supported methods: sum, multiply, multiply_sum, max

Argument 'numbers' should be a list of numbers split by comma.   
Number can be with floating point, examples: 5, 6.123, 35e-3  
Forbidden number format: 0x12  

Result can be taken from json response that looks like:  
{"result" : 1}

In case of illegal methods or numbers response format is:  
{"error" : "Unsupported method: 'maxx'"}

