package WizardTD;

public class Mana {

    float mana_value;
    float mana_pool_value;
    float mana_cap;
    float mana_pool_width;
    float mana_gain_speed;
    int temp;
    Gain_json gainJson;
    int frameRate;
    int mana_spell_cost = 100;

    Mana(float width, Gain_json gainJson, int frameRate) {
        this.mana_pool_width = width;
        this.gainJson = gainJson;
        this.frameRate = frameRate;
    }

    void setManaCap() {
        temp = (int)(gainJson.gain_method("initial_mana_cap"));
        mana_cap = Integer.valueOf(temp).floatValue();
    }

    void setManaValue() {
        temp = (int)(gainJson.gain_method("initial_mana"));
        mana_value = Integer.valueOf(temp).floatValue();
        mana_pool_value = mana_pool_width * (mana_value/mana_cap);
    }

    void setManaGainSpeed() {
        temp = (int)(gainJson.gain_method("initial_mana_gained_per_second"));
        mana_gain_speed = Integer.valueOf(temp).floatValue();
    }

    float getManaValue() {
        return mana_value;
    }

    float getManaPoolValue() {
        return mana_pool_value;
    }

    float getManaCap() {
        return mana_cap;
    }

    void countUp(float gameSpeed) {

        // count up mana
        if (mana_value < mana_pool_width) {
            mana_value += (mana_pool_width)*((mana_gain_speed/frameRate)/mana_cap)*gameSpeed;
        }
    }
    
    void countDown(float mana_cost) {

        // count down mana
        if ((mana_value-mana_cost) >= 0) {
            mana_value -= mana_cost;
        } else {
            mana_value = 0;
        }
        // update mana pool
        mana_pool_value = mana_pool_width * (mana_value/mana_cap);
    }

    void addUp(float mana_gain) {
        // count up mana
        mana_value += mana_gain;
        // update mana pool
        mana_pool_value = mana_pool_width * (mana_value/mana_cap);
    }

    void upgradeMana() {
        if (mana_value >= mana_spell_cost) {
            mana_value -= mana_spell_cost;
            mana_cap *= 1.5;
            mana_spell_cost += 150;
        }
        // update mana pool
        mana_pool_value = mana_pool_width * (mana_value/mana_cap);
    }
}
