package WizardTD;

public class fireBall {
    
    double fireBallSpeed;
    float Vx;
    float Vy;
    float monsterPosX;
    float monsterPosY;
    float fireBallPosX;
    float fireBallPosY;
    boolean isHit = false;
    Monsters target;
    float gameSpeed;
    
    //fireBall(float monsterPosX, float monsterPosY, float fireBallPosX, float fireBallPosY) {
    fireBall(Monsters target, float gameSpeed, float fireBallPosX, float fireBallPosY) {
        //this.monsterPosX = monsterPosX;
        //this.monsterPosY = monsterPosY;
        this.target = target;
        this.gameSpeed = gameSpeed;
        this.fireBallPosX = fireBallPosX;
        this.fireBallPosY = fireBallPosY;
        this.fireBallSpeed = 5*gameSpeed;
    }

    void changePos() {

        float[] monster_pos = target.getPos();

        monsterPosX = monster_pos[0]+6;
        monsterPosY = monster_pos[1]+46;

        //System.out.println("monsterPosX: " + monsterPosX);
        //System.out.println("monsterPosY: " + monsterPosY);

        // float angle = (float) ((Math.atan2(monsterPosY-fireBallPosY, monsterPosX-fireBallPosX) * 180) / Math.PI);
        // float ratio = (float) Math.tan(angle);

        // Vx = (float) (Math.sqrt((Math.pow(fireBallSpeed, 2) / (Math.pow(ratio, 2) + 1))));

        // Vy = (float) (Math.abs(ratio * Vx));
        double tempx = monsterPosX - fireBallPosX;
        double tempy = monsterPosY - fireBallPosY;
        double sum = Math.sqrt(Math.pow(tempx, 2) + Math.pow(tempy, 2));
        Vx = (float) (tempx / sum * fireBallSpeed);
        Vy = (float) (tempy / sum * fireBallSpeed);
        if (Math.abs(fireBallPosX - monsterPosX) < Vx && Math.abs(fireBallPosY - monsterPosY) < Vy) {
            isHit = true;
            target.madeDmage(40);
        }
        // if (fireBallPosX > monsterPosX) {
        //     fireBallPosX -= Vx;
        // } else {
        //     // fireBallPosX = monsterPosX;
        //     fireBallPosX += Vx;
        // }
        fireBallPosX += Vx;
        // if (fireBallPosY > monsterPosY) {
        //     fireBallPosY -= Vy;
        // } else {
        //     // fireBallPosY = monsterPosY;
        //     fireBallPosY += Vy;
        // }
        fireBallPosY += Vy;
        // check if the fireball hit the monster
        
    }

    boolean getIsHit() {
        return isHit;
    }

    boolean getIsOut() {

        if (target.inRange == false) {
            return true;
        } else {
            return false;
        }
    }

    boolean alive() {
        if (target.deathCheck()) {
            return false;
        } else {
            return true;
        }
    }

    float[] getFireBallPos() {
        float[] fireBallPos = {fireBallPosX, fireBallPosY};
        return fireBallPos;
    }
}
