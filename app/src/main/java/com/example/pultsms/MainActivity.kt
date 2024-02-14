package com.example.pultsms

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import com.example.pultsms.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import java.sql.Timestamp
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setSupportActionBar(binding.toolbar)
        Checkpermission()
     //   CreateButtonList()
        spinnerInitAdd()

    }



    fun radioButtonClik(v:View)
    {
 println(v)
        if (binding.radioButton1.id== v.id) {
            binding.radioButton2.isChecked = false
            binding.radioButton1.isChecked = true
            binding.LayoutOption1.visibility =  View.VISIBLE
            binding.LayoutOption2.visibility =  View.GONE


        }
        if (binding.radioButton2.id== v.id) {
            binding.radioButton1.isChecked = false
            binding.radioButton2.isChecked = true
            binding.LayoutOption2.visibility =  View.VISIBLE
            binding.LayoutOption1.visibility =  View.GONE
        }


    }

   fun radioButtonClik1 (v:View) =    binding.radioButton1.callOnClick()
   fun radioButtonClik2 (v:View) =    binding.radioButton2.callOnClick()
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
        Log.w("onCreateView","i")

    }



// функция для заполнения spinner
    fun spinnerInit( spin :Spinner,list :List<String>)
    {

        val adapter = ArrayAdapter (this,  R.layout.myspinnertext, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spin.adapter = adapter
       // android:spinnerMode="dialog

        spin.getSelectedItem().toString()
    }
    fun spinnerInitAdd() {
        val list = listOf<String>(
            "1 сек.",
            "5 сек.",
            "15 сек.",
            "30 сек.",
            "1 мин.",
            "5 мин.",
            "10 мин.",
            "15 мин.",
            )

        spinnerInit(binding.spinnerPeriodOP,list)
        spinnerInit(binding.spinnerPeriodSend,list)
        val list2 = listOf<String>(
            "30 мин.",
            "1 час.",
            "2 час.",
            "3 час.",
            "4 час.",
            "8 час.",
            "12 час.",
            "24 час.",
        )

        spinnerInit(binding.spinnerPeriodSend,list2)


        var array   = Array<String>(255) { i -> ( i).toString()+" мин." }.toList()
        spinnerInit(binding.spinnerWork,array)
        array   = Array<String>(127) { i -> ( i).toString()+"" }.toList()
        spinnerInit(binding.spinnerWork,array)



    }




    fun sendSms(number: String, sms: String) {

        try {


                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(number, null, sms, null, null)

            Toast.makeText(
                applicationContext,
                "SMS отправлено!", Toast.LENGTH_LONG
            ).show()
          //  val t = System.currentTimeMillis().toString()



        } //В случае фейла высвечиваем соответствующее сообщение:
        catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "SMS не отправлено, попытайтесь еще!", Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
        }
    }

    fun ShowInfoMessage(view: View)
    {
        val s= view.tag
        val builder = AlertDialog.Builder(this)
        builder//setTitle(" ")
            .setMessage(s.toString())
        val dialog = builder.create()
        dialog.show()
    }
    fun ComanndSend(view: View) {


//        val cmd=    binding.editTextSendMessageCommand.text.toString()
//        val phone=  binding.editTextSendMessageNumbler.text.toString()
//
//            Toast.makeText(this,"Команда  " + cmd+ " отправлена по номеру " +phone, Toast.LENGTH_LONG).show()
//            sendSms(phone,cmd)


    }
    fun CreateButtonList(view: View){
        CreateButtonList()
    }
    fun call(s: String) {
        val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + s))
        startActivity(call)
    }
    fun callSend(view: View)
    {
     //   call(binding.editTextSendMessageNumbler.text.toString())
    }
    fun Checkpermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.RECEIVE_SMS
            )
            ActivityCompat.requestPermissions(this, permissions, 0)
        }
    }

    fun CreateButtonList() {
        createButtonDynamically("buffer coord get count|!buffer coord get data 0|!buffer coord get remove 0",
            "запрос колво навигационных данных|запрос навигационных данных|удаление навигационных данных")
        createButtonDynamically("position", "получить местоположение")
        createButtonDynamically("@ussd", "ussd")
        createButtonDynamically("!set nav.period|get nav.period", "Установить период работы отправки координат|Получить период работы отправки координат")
        createButtonDynamically("mode get", "Получить режим работы")
        createButtonDynamically("mode set active|mode set standby|mode set collect|mode set timer","активный|дежурный|накопления|таймеру/датчикам" ,"Установить режим работы")
        createButtonDynamically("restart", "Перезапуск")
        createButtonDynamically("restart reset", "Сброс до заводских")
        createButtonDynamically("sсhedule set|sсhedule get", "Установить расписание мау|Прочитать расписание мау")


        createButtonDynamically("device 0 power on|device 0 power off|device 1 power on|device 1 power off",
            "включить 0 порт|выключить 0 порт|включить 1 порт|выключить 1 порт|")

        createButtonDynamically("version", "Версия")
        createButtonDynamically("Ttime set|time get", "установить время|Прочитать время")
        createButtonDynamically("test self", "тест себя","Тест")
        createButtonDynamically("test accelerometer", "тест акселлерометра","Тест")
        createButtonDynamically("test audio", "тест аудио","Тест")

        createButtonDynamically("!set system.id", "Индентификатор МАУ")
        createButtonDynamically("@set dc.ssid", "Имя WiFI ДС")
        createButtonDynamically("set system.chanel sms|set system.chanel wifi|set system.chanel gpshome|set system.chanel email",
            "отправка по смс |отправка по wifi|отправка на gpshome|отправка на email")
        createButtonDynamically("set system.masking on|set system.masking of", "Маскирование вкл|Маскирование выкл")
        createButtonDynamically("@set system.dns.server", "ip адрес dns сервера")

        createButtonDynamically("!set system.keepalive.sms", "Интервал keepalive sms сообщений", "СМС")
        createButtonDynamically("!set dc.phone", "Связь с дс Номер телефона", "СМС")
        createButtonDynamically("!set smscenter.phone", "Настройки смс центра Номер телефона", "СМС")
        createButtonDynamically("!set audiocontrol.phone", "Настройки аудиоконтроля Номер телефона", "СМС")

        createButtonDynamically("@set email.sever", " имя(IP) сервера", "Параметры почты:")
        createButtonDynamically("!set email.port", " порт(IP) сервера", "Параметры почты:")
        createButtonDynamically("@set email.user", " имя пользователя", "Параметры почты:")
        createButtonDynamically("@set email.password", "пароль пользователя", "Параметры почты:")
        createButtonDynamically("@set dc.email", "имя пользователя куда слать", "Почта")

        createButtonDynamically("@set gpshome.sever", "имя(IP) сервера", "Параметры GPShome:")
        createButtonDynamically("!set gpshome.port", "порт(IP) сервера", "Параметры GPShome:")
        createButtonDynamically("@set gpshome.id", "имя(ID) пользователя", "Параметры GPShome:")
        createButtonDynamically("@set gpshome.t-stop", "Интервал в секундах stop", "Параметры GPShome:")
        createButtonDynamically("@set gpshome.t-move", "Интервал в секундах move", "Параметры GPShome:")
        createButtonDynamically("!audiocontrol 1|audiocontrol 0", "аудиоконтроль вкл(99-999)|аудиоконтроль выкл","Аудионакопитель")
        createButtonDynamically("nRZ*", "Прочитать параметры", "Аудионакопитель")
        createButtonDynamically("nSZ*", "Установка параметров ", "Аудионакопитель")
        createButtonDynamically("nSS*", "Выключить запись", "Аудионакопитель")
        createButtonDynamically("nRE*", "Включить запись", "Аудионакопитель")
        createButtonDynamically("nRT0*|nRT1*", "Перезапуск|Сброс до заводских ", "Аудионакопитель")
        createButtonDynamically("nER*", "Стереть записи", "Аудионакопитель")
        createButtonDynamically("nRD*", "Запрос таблицы сеансов", "Аудионакопитель")
        createButtonDynamically("nRC", "Установить время", "Аудионакопитель")
        createButtonDynamically("nSP*|nTP*", "Включить работу по расписанию|Выключить работу по расписанию", "Аудионакопитель")
        createButtonDynamically("nRP*", "Чтение расписание автоматической записи", "Аудионакопитель")

        createButtonDynamically("photo take high digital card", "фотографирование цифровой камерой выс кач", "Фотонакопитель")
        createButtonDynamically("photo take low digital card", "фотографирование цифровой камерой выс кач", "Фотонакопитель")
        createButtonDynamically("photo take high analog card", "фотографирование аналоговой камерой", "Фотонакопитель")

        createButtonDynamically("photo set sсhedule", "установить расписание", "Фотонакопитель")
        createButtonDynamically("photo get sсhedule", "получить расписание", "Фотонакопитель")

        createButtonDynamically("photo reset", "сброс", "Фотонакопитель")
        createButtonDynamically("photo get time", "получить время", "Фотонакопитель")
        createButtonDynamically("photo card erase", "очистить карту памяти", "Фотонакопитель")
        createButtonDynamically("photo card get list", "получить список файлов", "Фотонакопитель")

        //  fun photoCardGetFile(filename: String) = "photo card get file $filename"
    }

    fun addComanndSend(cmd: String = "", name: String = "") {
        // var t= "#A"+textViewIP.text+"+U,"+cmd+"*"
        var t = cmd
//        if (cmd[0] == '!') //digit
//            showInput(cmd.drop(1), "!")
//        //   return
//
//        if (cmd[0] == '@') //text
//            showInput(cmd.drop(1))
//        //   return
//
//        if (cmd == "nSZ*")// установка параметров
//            set_audi_parm(contextGlob2)
//        if (cmd == "nSP*")
//            set_audi_rasrpisaniy(contextGlob2)
//
//        if (cmd == "sсhedule set")
//        // Show_audio(activity as MainActivityTest).set_audi_rasrpisaniy_mau(contextGlob2, last_cmd_set_audi_rasrpisaniy_mau)
//            set_audi_rasrpisaniy_mau(contextGlob2, last_cmd_set_audi_rasrpisaniy_mau)
//        if (cmd == "photo set sсhedule")
//            set_audi_rasrpisaniy_photo(contextGlob2)

        if (cmd == "nRC") {//nRC
            val stamp = Timestamp(System.currentTimeMillis())
            val date2 = SimpleDateFormat("ssmmHH").format(stamp) + "" + SimpleDateFormat("ddMMyy").format(stamp)//
            t = cmd + date2 + "*"
        }
        if (cmd[0] == 'T') {
            val stamp = Timestamp(System.currentTimeMillis())
            val date2 = " " + SimpleDateFormat("yyyy-MM-dd").format(stamp) + "T" + SimpleDateFormat("HH:mm:ss.SS").format(stamp) + "Z"//
            t = cmd.drop(1) + date2
        }

        if (t[0] == 'n')
            t = "aud:" + t
        else if (t[0] == '*')
            t = "pho:" + t
        else
            t = "mau:" + t

        Log.w("activity222",binding.toString())
//        binding.editTextSendMessageCommand?.setText(t)
//        binding.textViewDescription?.setText(name)


    }
    data class ff(val ll: LinearLayout, val title: String)

    val list_l = mutableListOf<ff>()
    var colorrrr = 255
    private fun createButtonDynamically(tag: String = "", namee: String = "", title: String = "") {

Log.w("createButtonDynamically",tag+namee+title)
        list_l.find { it.title == title } ?: run {
            var lll = LinearLayout(this@MainActivity)
            lll.orientation = 1
                    //   lll.setBackgroundColor(    Color.rgb(0, 0, colorrrr ))
            colorrrr -= 15
            val text = TextView(this@MainActivity)
            text.setText(title)
            lll.addView(text)
           binding.llSendCmd.addView(lll)


            list_l.add(ff(lll, title))
        }

        val ll = list_l.find { it.title == title }?.ll

        // creating the button
        var name = namee
        val dynamicButton = MaterialButton(this@MainActivity)
        if (name == "")
            name = tag

        dynamicButton.text = name
        dynamicButton.tag = tag
        dynamicButton.cornerRadius = 6
        //    dynamicButton.app:cornerRadius="2dp"
        val but_color=Color.rgb(91,157,200)
        dynamicButton.setBackgroundColor(but_color)
        dynamicButton.setOnClickListener() {
            addComanndSend(tag, name)
            ll?.let { it1 -> addComanndText(it1,"bt"+tag,"test ok" ) }//
            true
        }
        dynamicButton.setOnLongClickListener() {
            Toast.makeText(this, "OnLongClickListener", Toast.LENGTH_SHORT).show()
            var s=tag.replace(":!set ".toRegex() ,":get " )
            s=s.replace(":@set ".toRegex() ,":get " )
            addComanndSend(s, name)
            true
        }


        if (tag.split("|").size > 1)// equals()
        {

            val lll = LinearLayout(this)
            lll.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT




            )
            val nname = name.split("|")
            var i = 0

            for (el_tag in tag.split("|")) {
                val but = MaterialButton(this)
                if (nname.size > i)
                    name = nname[i]
                else
                    name = el_tag
                but.text = name
                but.tag = el_tag
                but.setBackgroundColor(but_color)
                but.setOnClickListener { addComanndSend(but.tag.toString(), but.text.toString())
                    ll?.let { it1 -> addComanndText(it1,"bt"+but.tag.toString(),"test ok" ) }
                }



                val dynamicBText= TextView(this)
                dynamicBText.text = ""
                dynamicBText.minWidth=92
                dynamicBText.maxWidth=142
                dynamicBText.tag = "bt"+but.tag

                but.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1F
                )
                dynamicBText.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1F
                )
                val lllz = LinearLayout(this)
                lllz.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
                lllz.orientation=1
                lllz.addView(but)
                lllz.addView(dynamicBText)
                lll.addView(lllz)

                i++
            }

            ll?.addView(lll)
            ll?.requestLayout()
        } else {
            //  ll_send_cmd.addView(dynamicButton)

            val dynamicBText= TextView(this)
            dynamicBText.text = ""
            dynamicBText.tag = "bt"+tag

            dynamicButton.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )

            dynamicBText.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )

            var lllz = LinearLayout(this)
            //lllz.orientation=0
            lllz.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
            )

            lllz  .addView(dynamicButton)
            lllz  .addView(dynamicBText)
            ll?.addView(lllz)
            ll?.requestLayout()
        }
    }
    private fun addComanndText(l: LinearLayout, tag: String = "", title: String = "")
    {
        return
        //  Log.d("addComanndText", tag+""+title)
        for (el in 0..l.childCount) {
            if (l.getChildAt(el) is LinearLayout) {
                Log.d("addComanndText",  "LinearLayout")
                addComanndText(l.getChildAt(el) as LinearLayout, tag, title)
            }
            for (el in 0..l.childCount) {
                if (l.getChildAt(el) is TextView)
                    if ((l.getChildAt(el) as TextView).tag == tag){
                        (l.getChildAt(el) as TextView).text = title
                        break
                    }
            }

        }
    }
}