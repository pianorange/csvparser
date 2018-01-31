package csvParsor

import java.io.File
import java.nio.file.Paths

class CsvParser {

    companion object {
        var strarray: ArrayList<String> = arrayListOf()
        var resultMap: LinkedHashMap<Int,ArrayList<Any>> = LinkedHashMap<Int, ArrayList<Any>>()
    }


    fun csvFileToString(filePath:String) {

        val inputStream = File(Paths.get("").toAbsolutePath().toString()
                .plus(filePath)).inputStream()
//        var jsonString = inputStream.bufferedReader().use { it.readText() }
//        strarray = jsonString.split("/n") as ArrayList<String>

        val reader = inputStream.bufferedReader()
        var iterator = reader.lineSequence().iterator()

        while (iterator.hasNext()){
            var lineText:String = iterator.next()
           strarray.add(lineText)
        }
    }



    fun convertCsvContents(content : ArrayList<String>):Any{

        if (strarray == null || strarray.size <= 0){
            return "content is empty"
        }

        var currentBlock: StringBuilder? = StringBuilder()
        var readingEscapeBlockFlag : Boolean = false
        var perRowArray: ArrayList<Any> = ArrayList<Any>()
        var braceCount:Int = 0

        for ((index:Int, row:String) in content.withIndex()){
            var strarray : CharArray  = row.toCharArray()
            var strarraySize:Int = strarray.size

            for ((index:Int, value:Char) in strarray.withIndex()) {
               when(value){

                   '"' -> {

                       ++braceCount
                        // currentBlock.toString().count{it == '"'}
                       // """ 책 "" 잡지 """""
                       // "" 처리 다음자리 " 이고 짝수 번째 " 일때만 추가한다 """ 같이 연속되서 나올때를 위한 처리
                       if(index < strarraySize-1
                                && strarray.get(index+1) == '"'
                                && braceCount > 1
                                && braceCount % 2 == 0){

                            currentBlock!!.append(value)
                        //첫번째 이스케이프 판별하기 위한 처리
                        }else if(braceCount == 1){

                            readingEscapeBlockFlag = true
                        //마지막 이스케이프 " 판별하기 위한 처리
                        // ""는 무조건 짝수개가 사용되어야 하고 " 다음에 "가 없다는 점, ""가 연속해서 왔을 경우 이미 위에 "" 를 위한 첫번째 분기에서
                           //걸러졌으므로 마지막이라고 단정 지을 수 있다.
                        }else if(index == strarraySize - 1 ||
                                braceCount > 1
                                && braceCount % 2 == 0
                                && index < strarraySize - 1 && strarray.get(index+1) != '"'){

                           braceCount = 0
                            readingEscapeBlockFlag = false

                           if(index == strarraySize - 1){
                               perRowArray.add(currentBlock.toString())
                               currentBlock = StringBuilder()
                           }

                        }
                   }
                   ',' -> {
                       //  ,,, 일 경우
                      if(index == 0 || index > 0 && strarray.get(index-1) == ','){
                            perRowArray.add("")
                          if(index == strarraySize-1) {
                              perRowArray.add("")
                          }
                        }else if(readingEscapeBlockFlag == true){
                            currentBlock!!.append(value)
                        }else if(readingEscapeBlockFlag == false){

                          perRowArray.add(currentBlock.toString())

                          if(index == strarraySize-1) {
                              perRowArray.add("")
                          }
                            currentBlock = StringBuilder()
                        }
                   }

                   else -> {
                        currentBlock!!.append(value)
                       if(index == strarraySize - 1){
                           perRowArray.add(currentBlock.toString())
                           currentBlock = StringBuilder()
                       }
                   }
               }
            }
            //row한개를 완성할 때마다 담아준다.
            resultMap.put(index,perRowArray)
            perRowArray = ArrayList<Any>()
        }
        return resultMap.toString()
    }

    fun typeCheck(checkval:String) : Boolean{
            try {
                checkval.toDoubleOrNull()
                return true
            } catch (e : NumberFormatException) {
                println(e.stackTrace)
                return false
            }
    }


}