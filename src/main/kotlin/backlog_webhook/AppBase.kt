package backlog_webhook

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.InputStream
import java.io.OutputStream

abstract class AppBase {

    lateinit var request: JsonObject
    lateinit var jsonObj: JsonObject

    lateinit var input: InputStream
    lateinit var output: OutputStream
    lateinit var context: Context

    lateinit var logger: LambdaLogger

    fun handler(input: InputStream, output: OutputStream, context: Context) {
        this.input = input
        this.output = output
        this.request = Parser().parse(input) as JsonObject
        this.jsonObj = this.request.getObj("requestParameters")
        this.context = context
        this.logger = context.logger

        logger.log("request : ${request.toJsonString()}")

        handlerSub()
    }

    abstract fun handlerSub()
}