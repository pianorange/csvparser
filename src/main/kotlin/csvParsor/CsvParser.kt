package csvParsor

import csvParsor.util.FileReader
import java.math.BigDecimal
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.*

class CsvParser {

    companion object {

        var strarray: ArrayList<String> = arrayListOf()
        var resultMap: LinkedHashMap<Int,ArrayList<Any>> = LinkedHashMap<Int, ArrayList<Any>>()
    }

    fun getData(filePath:String){
        var fileReader:FileReader = FileReader()
        fileReader.csvFileToString(filePath)
    }

    fun convertCsvContents():Any{

        if (strarray == null || strarray.size <= 0){
            return "content is empty"
        }

        var content : ArrayList<String> = strarray

        var currentBlock: StringBuilder? = StringBuilder()
        var readingEscapeBlockFlag : Boolean = false
        var strPerRowArray: ArrayList<String> = ArrayList<String>()
        var perRowArray: ArrayList<Any> = ArrayList<Any>()
        var braceCount:Int = 0

        for ((index:Int, row:String) in content.withIndex()){
            var rowStrArray : CharArray  = row.toCharArray()
            var rowStrArraySize:Int = rowStrArray.size

            for ((index:Int, value:Char) in rowStrArray.withIndex()) {
               when(value){

                   '"' -> {
                       ++braceCount

                       // "" 처리: 다음자리 " 이고 짝수 번째 " 일때만 추가한다 """ 같이 연속되서 나올때를 위한 처리
                       if(index < rowStrArraySize-1
                                && rowStrArray.get(index+1) == '"'
                                && braceCount > 1
                                && braceCount % 2 == 0){

                            currentBlock!!.append(value)

                        //첫번째 이스케이프 판별하기 위한 처리
                        }else if(braceCount == 1){

                            readingEscapeBlockFlag = true

                        //마지막 이스케이프 " 판별하기 위한 처리
                        // ""는 무조건 짝수개가 사용되어야 하고 " 다음에 "가 없다는 점, ""가 연속해서 왔을 경우 이미 위에 "" 를 위한 첫번째 분기에서
                        // 걸러졌으므로 마지막이라고 단정 지을 수 있다.
                        }else if(index == rowStrArraySize - 1 ||
                                braceCount > 1
                                && braceCount % 2 == 0
                                && index < rowStrArraySize - 1 && rowStrArray.get(index+1) != '"'){

                            braceCount = 0
                            readingEscapeBlockFlag = false

                           if(index == rowStrArraySize - 1){
                               strPerRowArray.add(currentBlock.toString())
                               currentBlock = StringBuilder()
                           }
                        }
                   }
                   ',' -> {
                       //  ,,, 일 경우
                      if(index == 0 || index > 0 && rowStrArray.get(index-1) == ','){
                            strPerRowArray.add("")
                          if(index == rowStrArraySize-1) {
                              strPerRowArray.add("")
                          }
                        }else if(readingEscapeBlockFlag == true){
                            currentBlock!!.append(value)
                        }else if(readingEscapeBlockFlag == false){

                          strPerRowArray.add(currentBlock.toString())

                          if(index == rowStrArraySize-1) {
                              strPerRowArray.add("")
                          }
                            currentBlock = StringBuilder()
                        }
                   }
                   else -> {
                        currentBlock!!.append(value)
                       if(index == rowStrArraySize - 1){
                           strPerRowArray.add(currentBlock.toString())
                           currentBlock = StringBuilder()
                       }
                   }
               }
            }
            //strrow 배열 타입체크해서 래퍼클래스에 담는다
              perRowArray = typeCheck(strPerRowArray)
            //row한개를 완성할 때마다 담아준다.
            resultMap.put(index,perRowArray)

            //row 초기화: str배열, 랩핑한 배열 초기화
            strPerRowArray = ArrayList<String>()
            perRowArray = ArrayList()
        }
        return resultMap.toString()
    }

    fun typeCheck(checkval:ArrayList<String>): ArrayList<Any> {

        var result =  ArrayList<Any>()

        //   Int, Double, Boolean, String, Date, Null
        for(obj in checkval){
            if (obj.toIntOrNull() != null){
                result.add(obj.toInt())
                break
            }
            else if (obj.toDoubleOrNull() != null) {
                result.add(obj.toDouble())
                break
            }
            else if ("TRUE".equals(obj.toUpperCase()) || "FALSE".equals(obj.toUpperCase())){
                result.add(obj.toBoolean())
                break
             }
            else {

                var date: LocalDate? = changeToDateOrNull(obj)

                if (date != null) {
                    result.add(date)
                    break
                }else {
                    result.add(obj)
                }
            }
      }
        return result
    }




    fun changeToDateOrNull(rawData:String): LocalDate?{

        val sluuuuMMdd =DateTimeFormatter.ofPattern("uuuu/MM/dd")
        val slyymmdd = DateTimeFormatter.ofPattern("yy/mm/dd")

        val formatSet: Set<DateTimeFormatter> =
                setOf( DateTimeFormatter.ISO_DATE,sluuuuMMdd, slyymmdd)

        var result:LocalDate? = null

        for (format in formatSet){
            try {
                result = LocalDate.parse(rawData,format)

            }catch (ignore :Exception){

            }
        }
        return result
    }

}