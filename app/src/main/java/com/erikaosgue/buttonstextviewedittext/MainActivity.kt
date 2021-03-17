package com.erikaosgue.buttonstextviewedittext

import android.graphics.Color
import android.graphics.PorterDuff
import com.erikaosgue.buttonstextviewedittext.databinding.ActivityMainBinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.CheckBox
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random




class MainActivity : AppCompatActivity(), View.OnClickListener{


    lateinit var activityMainBinding: ActivityMainBinding

    var hashPlanetGravity = hashMapOf<String, Double>()
    var planetsGravity = hashMapOf(
            "mars" to 0.38,
            "mercury" to 0.38,
            "venus" to 0.91,
            "earth" to 1.00,
            "jupiter" to 2.34)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.imageViewId.setOnClickListener {
            val listOfColors = listOf<Int>(Color.CYAN, Color.BLUE, Color.GREEN, Color.BLACK, Color.MAGENTA)
            val randomNumber = Random.nextInt(listOfColors.size)
            activityMainBinding.imageViewId.setColorFilter(listOfColors[randomNumber], PorterDuff.Mode.OVERLAY)
        }


        // enterWeightId is the EditText (PlainText we created)
        val weight = enterWeightId.text


        // Get our Button
        showWeightButtonId.setOnClickListener {
            var output = ""


            if (weight.isNotBlank()) {
                val result = calculateWeight(weight.toString().toDouble())

                if (result.containsKey("empty"))
                    output = "Select the Planets you want to know your weight!"
                else {
                    output += "Your weight is: \n"
                    for ((planet, wei) in result) {
                        output += "-> $wei in $planet\n"
                    }
                }
            } else {
                output = "Enter a weight Number"
            }
            resultTextViewId.text = output
        }

        // Registering the click Listeners
        marsCheckBox.setOnClickListener(this)
        earthCheckBox.setOnClickListener(this)
        jupiterCheckBox.setOnClickListener(this)

    }

    private fun calculateWeight(userWeight: Double): HashMap<String, Double> {

        // Calculate weight baseOn the planets that the hashPlanetGravity Contains
        return if (hashPlanetGravity.isNotEmpty()) {
            for ((key) in hashPlanetGravity) {
                hashPlanetGravity[key] = (userWeight * planetsGravity[key]!!).format(2).toDouble()
            }
            hashPlanetGravity
        } else {
            hashMapOf("empty" to 0.0)
        }

    }


    override fun onClick(view: View?) {
//        fun onCheckBoxClicked(view: View) { this function is replace by onClick
            view as CheckBox
            val isCheckedBox: Boolean = view.isChecked
            when (view.id) {
                R.id.earthCheckBox -> {
                    if (isCheckedBox) {
                        Log.d("earth", "isChecked")
                        hashPlanetGravity["earth"] = planetsGravity["earth"] ?: 0.0
                    } else {
                        Log.d("earth", "noChecked")
                        hashPlanetGravity["earth"].let {
                            hashPlanetGravity.remove("earth")
                        }
                    }
                }
                R.id.marsCheckBox -> {

                    if (isCheckedBox) {
                        Log.d("mars", "isChecked")
                        hashPlanetGravity["mars"] = planetsGravity["mars"] ?: 0.0
                    } else {
                        Log.d("mars", "noChecked")
                        hashPlanetGravity["mars"].let {
                            hashPlanetGravity.remove("mars")
                        }
                    }
                }
                R.id.jupiterCheckBox -> {
                    if (isCheckedBox) {
                        Log.d("jupiter", "isChecked")
                        hashPlanetGravity["jupiter"] = planetsGravity["jupiter"] ?: 0.0
                    } else {
                        Log.d("jupiter", "noChecked")
                        hashPlanetGravity["jupiter"].let {
                            hashPlanetGravity.remove("jupiter")
                        }
                    }
                }

            }
        }

    fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)
}


/**
    /*************************** The Example **************************************/
class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var activityMainBinding: ActivityMainBinding

    val marsGravity = 0.38
    val earthGravity = 1.00
    val jupiterGravity = 2.34

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        showWeightButtonId.setOnClickListener {
            // var result = calculateWeight(weight.toString().toDouble())

            //resultTextViewId.text = "You weight " + result.toString() + " on Mars"

        }

        marsCheckBox.setOnClickListener(this) // registering our clicklistener
        earthCheckBox.setOnClickListener(this)
        jupiterCheckBox.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        v as CheckBox
        var isChecked: Boolean = v.isChecked
        var weight = enterWeightId.text


        when(v.id) {
            R.id.marsCheckBox -> if (isChecked && !TextUtils.isEmpty(enterWeightId.text.toString()))
            {
                calculateWeight(weight.toString().toDouble(), v)
                earthCheckBox.isChecked = false
                jupiterCheckBox.isChecked = false
            }
            R.id.earthCheckBox -> if (isChecked && !TextUtils.isEmpty(enterWeightId.text.toString()))
            {
                calculateWeight(weight.toString().toDouble(), v)
                marsCheckBox.isChecked = false
                jupiterCheckBox.isChecked = false
            }
            R.id.jupiterCheckBox -> if (isChecked && !TextUtils.isEmpty(enterWeightId.text.toString()))
            {
                calculateWeight(weight.toString().toDouble(), v)
                marsCheckBox.isChecked = false
                earthCheckBox.isChecked = false
            }
        }

    }

    fun calculateWeight(userWeight: Double, checkBox: CheckBox) { // 89.78
        var result: Double?
        when(checkBox.id) {

            R.id.marsCheckBox -> {
                result = userWeight * marsGravity
                resultTextViewId.text = "Weight is " + result.format(2) + " On Mars"
            }
            R.id.earthCheckBox -> {
                result = userWeight * earthGravity
                resultTextViewId.text = "Weight is " + result.format(2) + " On earth"
            }
            R.id.jupiterCheckBox ->  {
                result = userWeight * jupiterGravity
                resultTextViewId.text = "Weight is " + result.format(2) + " On Jupiter"
            }
            else -> result = userWeight * marsGravity

        }
    }

    fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)
}
        **/