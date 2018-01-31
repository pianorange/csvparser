Adjacent fields must be separated by a single comma. However, "CSV" formats vary greatly in this choice of separator character. In particular, in locales where the comma is used as a decimal separator, semicolon, TAB, or other characters are used instead.

    1997,Ford,E350
Any field may be quoted (that is, enclosed within double-quote characters). Some fields must be quoted, as specified in following rules.
<br>모든 필드는 따옴표로 묶을 수 있습니다 (즉, 큰 따옴표로 묶습니다). 일부 필드는 다음 규칙에 지정된대로 따옴표로 묶어야합니다. 
    
    "1997","Ford","E350"

Fields with embedded commas or double-quote characters must be quoted.
<br>    

    1997,Ford,E350,"Super, luxurious truck"
Each of the embedded double-quote characters must be represented by a pair of double-quote characters.
<br> 쌍따옴표는 반드시 쌍따옴표 쌍("")으로표현한다. 

    1997,Ford,E350,"Super, ""luxurious"" truck"
Fields with embedded line breaks must be quoted (however, many CSV implementations do not support embedded line breaks).
<br>    개행이 포함된 라인은 반드시 쌍따옴표 안에 표현한다.

    1997,Ford,E350,"Go get one now
    they are going fast"

In some CSV implementations[which?], leading and trailing spaces and tabs are trimmed (ignored). Such trimming is forbidden by RFC 4180, which states "Spaces are considered part of a field and should not be ignored."
<br>    

    1997, Ford, E350
not same as
    
    1997,Ford,E350
According to RFC 4180, spaces outside quotes in a field are not allowed; however, the RFC also says that "Spaces are considered part of a field and should not be ignored." and "Implementors should 'be conservative in what you do, be liberal in what you accept from others' (RFC 793 [8]) when processing CSV files."
<br>    쌍따옴표 밖의 공백은 허용되지 않는다는데 공백은 필드의 일부이며 공백 무시하면안된다는 말도있다고함
        권고사항인듯 표준규약도 없는듯

    1997, "Ford" ,E350
In CSV implementations that do trim leading or trailing spaces, fields with such spaces as meaningful data must be quoted.
<br>   

    1997,Ford,E350," Super luxurious truck "
Double quote processing need only apply if the field starts with a double quote. Note, however, that double quotes are not allowed in unquoted fields according to RFC 4180.
<br>
    
    Los Angeles,34°03′N,118°15′W
    New York City,40°42′46″N,74°00′21″W
    Paris,48°51′24″N,2°21′03″E
The first record may be a "header", which contains column names in each of the fields (there is no reliable way to tell whether a file does this or not; however, it is uncommon to use characters other than letters, digits, and underscores in such column names).
<br>

    Year,Make,Model
    1997,Ford,E350
    2000,Mercury,Cougar
