package web.util.svg

import org.apache.batik.transcoder.Transcoder
import org.apache.batik.transcoder.TranscoderException
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import java.io.OutputStream

import java.io.*;



@Throws(IOException::class, TranscoderException::class)
fun  svg2PNG( svgCode:String,  out: OutputStream) {
    val transcoder = PNGTranscoder()
    svgConverte(svgCode, out, transcoder)
}

@Throws(IOException::class, TranscoderException::class)
private fun svgConverte(svgCode: String, out: OutputStream, transcoder: Transcoder) {
    val processedSvg = svgCode.replace(":rect", "rect")
    val input = TranscoderInput(ByteArrayInputStream(processedSvg.toByteArray()))
    val output = TranscoderOutput(out)
    svgConverte(input, output, transcoder)
}


@Throws(IOException::class, TranscoderException::class)
private fun svgConverte(input: TranscoderInput, output: TranscoderOutput, transcoder: Transcoder) {
    transcoder.transcode(input, output)
}