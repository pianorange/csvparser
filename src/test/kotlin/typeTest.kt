import csvParsor.CsvParser
import org.junit.Test
import  org.junit.Assert
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class typeTest {

    var parser:CsvParser = CsvParser()
    var targetList : ArrayList<String> = ArrayList()
    var resultList : ArrayList<Any> = ArrayList()

    @Test
    fun checkDoubleParsing(){

        targetList.add("2.0")
        resultList = parser.typeCheck(targetList)

        var result : Any = resultList.get(0)
        var expected : Double = 2.0

        Assert.assertEquals(expected , result)
    }

    @Test
    fun checkIntParsing(){
        targetList.add("10")
        resultList = parser.typeCheck(targetList)

        var result = resultList.get(0)
        var expected : Int = 10

        Assert.assertEquals(expected,result)
    }

    @Test
    fun checkIntParsing_Minus(){
        targetList.add("-1")
        resultList = parser.typeCheck(targetList)

        var result = resultList.get(0)
        var expected : Int = -1

        Assert.assertEquals(expected,result)
    }

    @Test
    fun checkBooleanParsing() {
        targetList.add("true")
        resultList = parser.typeCheck(targetList)

        var result = resultList.get(0)
        var expected : Boolean = true

        Assert.assertEquals(expected, result)
    }

    @Test
    fun checkDateParsing() {
        targetList.add("2018-12-28")
        resultList = parser.typeCheck(targetList)

        var result = resultList.get(0)
        var expected : LocalDate = LocalDate.of(2018, 12,28)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun checkString(){
        targetList.add("홍길동")
        resultList = parser.typeCheck(targetList)

        var result = resultList.get(0)
        var expected : String = "홍길동"
        Assert.assertEquals(expected,result)
    }
}