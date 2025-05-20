package vcmsa.mbali.myassignment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }//end of ViewCompat
        //Code goes here
        //link the element from the GUI to the backend
        val tvQuestion = findViewById<TextView>(R.id.tvQuizQuestion)
        val rbtngAnswers = findViewById<RadioGroup>(R.id.rbtngQuizAnswers)
        val btnNext =  findViewById<Button>(R.id.button)
        val username = intent.getStringExtra("username")
        //Arrays of Questions and Answers
        val historicalQuestions = arrayOf(
            "Did Nelson Mandela incint a strike related to Black peoples freedom and rights? ",
            "Was Nelson arrested for high treason? ",
            "Nelson was arrested for 15 years?",
            "Was World war one in 1914? ",
            "World wars still exists?"

        )
        val answersChoice = arrayOf(
            arrayOf("TRUE", "FALSE"),
            arrayOf("TRUE", "FALSE"),
            arrayOf("TRUE", "FALSE"),
            arrayOf("TRUE", "FALSE"),
            arrayOf("TRUE", "FALSE")

        )
        val userAnswers = arrayOfNulls<String>(5)
        val correctAnswers = arrayOf(
            "TRUE",
            "FALSE, striking for freedom",
            "FALSE, 27 YEARS",
            "TRUE",
            "TRUE",

        )
        var counter = 0
        //code logic
        //Set the first question before waiting fir user interaction
        tvQuestion.text = historicalQuestions[counter]
        for (i in 0 until rbtngAnswers.childCount) {
            val radioButton = rbtngAnswers.getChildAt(i) as RadioButton
            radioButton.text = answersChoice[counter][i]
        }
        btnNext.setOnClickListener {
            if(counter < 5) {
                val chosenOption = rbtngAnswers.checkedRadioButtonId

                if (chosenOption != -1) {
                    val chosenRbtn = findViewById<RadioButton>(chosenOption)
                    userAnswers[counter] = chosenRbtn.text.toString()
                    counter++
                }else {

                    Toast.makeText(this, "please select a answer", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener // Stop performace if no answer is selected
                }

                if (counter < 5){
                    tvQuestion.text = historicalQuestions[counter]
                    for (i in 0 until rbtngAnswers.childCount) {
                        val radioButton = rbtngAnswers.getChildAt(i) as RadioButton
                        radioButton.text = answersChoice[counter][i]
                    }
                    rbtngAnswers.clearCheck()
                } else {
                    val intent = Intent(this, ResultsActivity::class.java)
                    var score = 0


                    //calculate score
                    for (i in userAnswers.indices) {
                        if (userAnswers[i] == correctAnswers[i]) {
                            score++
                        }
                    }

                    intent.putExtra("score", score)
                    intent.putExtra("username", username)

                    startActivity(intent)
                    finish()
                }
            }
        }
    }//end of onCreate
}//end of QuizActivity