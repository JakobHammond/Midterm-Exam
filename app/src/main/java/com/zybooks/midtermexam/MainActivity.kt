package com.zybooks.midtermexam


import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.Button
import kotlin.random.Random



class MainActivity : AppCompatActivity() {

    var poohsition: Float = 0.0f //The branch Pooh is currently on
    val initPos: Float = 1526.0f //Pooh's starting y location
    var rolls: Int = 0           //Number of rolls performed in the game




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val PoohView: TextView = findViewById(R.id.poohView)    //The bear himself
        PoohView.text = "ʕ•ᴥ•ʔ" //Emoticon to represent Pooh

        val dialogueView: TextView = findViewById(R.id.dialogueDisplay)
        dialogueView.x = 50.0f
        dialogueView.y = 2000.0f


        val rButton: Button = findViewById(R.id.rollButton)
        rButton.x = 400.0f
        rButton.y = 1600.0f

        val roll1Display: TextView = findViewById(R.id.roll1Display)
        val roll2Display: TextView = findViewById(R.id.roll2Display)

        roll1Display.y = 1650.0f
        roll2Display.y = 1650.0f

        roll1Display.x = 200.0f
        roll2Display.x = 850.0f

        val restartButton: Button = findViewById(R.id.restartButton)
        restartButton.x = 800.0f
        restartButton.y = 1000.0f

        val rollsView: TextView = findViewById(R.id.rollsView)
        rollsView.x = 800.0f
        rollsView.y = 800.0f


        rButton.setOnClickListener{



            //Both dice roll a number from 1 to 6
            val firstDie: Int = Random.nextInt(1, 6)
            val secondDie: Int = Random.nextInt(1, 6)

            //Update both dice's displays to show the number that was rolled
            roll1Display.text = firstDie.toString()
            roll2Display.text = secondDie.toString()

            //First possibility: doubles cause Pooh to fall four branches
            if(firstDie == secondDie){
                if(poohsition <= 4) {
                    poohsition = 0.0f
                    initPos - (poohsition * 49) //Moves Pooh based on his current position
                    dialogueView.text = "Doubles! Doubles! Pooh fell to the ground!"
                }
                else {
                    poohsition -= 4
                    PoohView.y =
                        initPos - (poohsition * 49) //Moves Pooh based on his current position
                    dialogueView.text = "Doubles! Doubles! Pooh fell four branches!"
                }
            }
            //Second possibility: a total of four causes Pooh to not climb
            else if(firstDie + secondDie == 4){
                dialogueView.text = "A strong gust of wind prevents Pooh from ascending!"
            }
            //Final possibility: Pooh ascends the tree by a number of branches corresponding to the roll
            else {
                var totalRoll = firstDie + secondDie
                poohsition += totalRoll
                PoohView.y = initPos - (poohsition * 49) //Moves Pooh based on his current position
                //49 is just the distance between branches
                dialogueView.text =
                    "You rolled " + totalRoll + "; Pooh has now reached branch #" + poohsition
            }
        //There might be bees... (you lose a turn)
        if(poohsition == 10.0f || poohsition == 20.0f){
            dialogueView.text = "Uh oh! Branch " + poohsition + " is home to bees! You lose a turn!"
            rolls += 1
        }
        else if(poohsition >= 30.0f){
            poohsition = 30.0f
            PoohView.y = initPos - (poohsition * 49) //Moves Pooh based on his current position
            dialogueView.text = "You reached branch 30 and therefore won! Enjoy the honey!"
        }

        rolls += 1

            rollsView.text = "Rolls: " + rolls
        }

        restartButton.setOnClickListener{
            poohsition = 0.0f
            PoohView.y = initPos - (poohsition * 49) //Moves Pooh based on his current position
            rolls = 0
            rollsView.text = "Rolls: " + rolls
            dialogueView.text = "Game Restarted"
        }



    }



}
