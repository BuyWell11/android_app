package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import com.example.myapplication.databinding.FightBinding
import kotlinx.android.parcel.Parcelize

class FightActivity : AppCompatActivity() {
    lateinit var binding:FightBinding
    lateinit var state: State

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lowdmg.setOnClickListener{
            onLDSpressed()
        }
        binding.middmg.setOnClickListener{
            onMDSpressed()
        }
        binding.highdmg.setOnClickListener{
            onHDSpressed()
        }
        binding.mana.setOnClickListener{

        }
        state = savedInstanceState?.getParcelable(KEY_STATE) ?: State(
            hp = 100,
            boss_hp = 1000
        )
    }

    private fun onLDSpressed()
    {
        val random = (0..1).random()
        if (random == 0) {
            val intent = Intent(this, LdsWordActivity::class.java)
            startActivityIfNeeded(intent, LOW_DMG_REQUEST_CODE)
        }
        else if(random == 1)
        {
            val intent = Intent(this, LdsPictureActivity::class.java)
            startActivityIfNeeded(intent, LOW_DMG_REQUEST_CODE)
        }
    }

    private fun onMDSpressed()
    {
        val random = (0..1).random()
        if (random == 0) {
            val intent = Intent(this, HdsTranslationActivity::class.java)
            startActivityIfNeeded(intent, MID_DMG_REQUEST_CODE)
        }
        else if(random == 1) {
            val intent = Intent(this, HdsChooseWordActivity::class.java)
            startActivityIfNeeded(intent, MID_DMG_REQUEST_CODE)
        }
    }

    private fun onHDSpressed()
    {
        val random = (0..1).random()
        if (random == 0) {
            val intent = Intent(this, HdsTranslationActivity::class.java)
            startActivityIfNeeded(intent, HIGH_DMG_REQUEST_CODE)
        }
        else if(random == 1) {
            val intent = Intent(this, HdsChooseWordActivity::class.java)
            startActivityIfNeeded(intent, HIGH_DMG_REQUEST_CODE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_STATE, state)
    }

    private fun renderState() = with(binding) {
        monsterhp.text = "HP: ${state.boss_hp}/1000"
        wizhp.text = "HP: ${state.hp}/100"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var temp_bool:Boolean
        var right = data?.getBooleanExtra(LdsPictureActivity.RIGHT, false)
        when(requestCode){
            LOW_DMG_REQUEST_CODE -> {
                if(right == true){
                    state.boss_hp -= 100
                }
                else{
                    state.hp -= 20
                }
        }
            MID_DMG_REQUEST_CODE ->{
                if(right == true){
                    state.boss_hp -= 250
                }
                else{
                    state.hp -= 20
                }
            }
            HIGH_DMG_REQUEST_CODE ->{
                if(right == true){
                    state.boss_hp -= 400
                }
                else{
                    state.hp -= 20
                }
            }
        }
        renderState()
        if(state.hp <= 0){
            temp_bool = true
            fight_finish(temp_bool)
        }
        else if(state.boss_hp <= 0){
            temp_bool = false
            fight_finish(temp_bool)
        }
    }

    private fun fight_finish(dead:Boolean){
        var intent = Intent(this, Win_lossActivity::class.java)
        intent.putExtra(Win_lossActivity.RESULT, dead)
        startActivity(intent)
        finish()
    }

    @Parcelize
    class State(
        var hp: Int,
        var boss_hp: Int
    ) : Parcelable

    companion object {
        @JvmStatic private val KEY_STATE = "STATE"
        @JvmStatic private val LOW_DMG_REQUEST_CODE = 1
        @JvmStatic private val MID_DMG_REQUEST_CODE = 2
        @JvmStatic private val HIGH_DMG_REQUEST_CODE = 3
    }
}