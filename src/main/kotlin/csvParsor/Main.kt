package csvParsor

import java.io.File
import java.nio.file.Paths

class Main {
    companion object {
        val filePath:String = "/src/main/resources/SampleCSVFile_2kb.csv"
        val filePath11:String = "/src/main/resources/SampleCSVFile_11kb1.csv"
        val filePathtest:String =  "/src/main/resources/test.csv"
    }

}

fun main(args: Array<String>) {

var a: CsvParser  = CsvParser()

    a.getData(Main.filePath)
    println("csv 스트링 원본 배열 싸이즈${CsvParser.strarray.size }")
    println( "원본 배열 내용 ${CsvParser.strarray}" )

    a.convertCsvContents()

    var values: MutableCollection<ArrayList<Any>> = CsvParser.resultMap.values
     values.stream().forEach { println(it) }

}