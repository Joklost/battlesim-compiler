public class Map extends JPanel implements ActionListener {


    // Denne bliver kaldt hvert frame
    public void actionPerformed(ActionEvent actionEvent) {
        if(isWinnerFound()){
            return;
        } else {
            updatePositions();
            performInstructions();
        }
    }

    public void initSteps(){
        Step eStep1 = new MoveStep(e, new Coord(950,250));
        Step eStep2 = new MoveStep(e, new Coord(600,260));
        Step eStep3 = new DetectEnemiesStep(e, 30 * 60);


        Step aGroup1Step1 = new MoveStep(aGroup1, new Coord(600,300));
        Step aGroup2Step1 = new MoveStep(aGroup2, new Coord(600,200));
        Step aStep2 = new MoveStep(a, new Coord(300, 250));
        Step aGroup1Step3 = new MoveAndDetectStep(aGroup1, new Coord(600,250));
        Step aGroup2Step3 = new MoveAndDetectStep(aGroup2, new Coord(600,240);
        Step aGeneralStep4ChoiceA = new Move(aGeneral, new Coord(600,250));
        Step aGeneralStep4ChoiceB = new Move(aGeneral, Base);
    }

    private void updatePositions(){
        //...
    }

    private void performInstructions(){
        //Enemies
        eStep1.RunIfCanStart();
        eStep2.RunIfCanStart();
        eStep3.RunIfCanStart();

        //Allies
        aGroup1Step1.RunIfCanStart();
        aGroup2Step1.RunIfCanStart();
        aStep2.RunIfCanStart();
        aGroup1Step3.RunIfCanStart();
        aGroup2Step3.RunIfCanStart();
        aGeneralStep3.RunIfCanStart();
        if(aGroup1Step3.isDone && aGroup2Step3.isDone){ //Dette er en WaitFor i BattleSim
            if(!aGroup1.isDead && !aGroup2.isDead)
                aGeneralStep4ChoiceA.RunIfCanStart();
            else
                aGeneralStep4ChoiceB.RunIfCanStart();
        }
    }
    //...fra vores PoC

}

public abstract class abstract{
    public boolean isDone = false;
    public DynObj object; //Soldier, Group, Platoon og Force nedarver fra DynObj (Dynamisk objekt)

    public Step(DynObj object){
        this.object = object;
    }

    //Hvis dette step ikke er færdig og objektet ikke bliver kontrolleret af andre end dette step selv så retuner true
    public boolean CanStart(){
        return !isDone && (!object.isControlled || object.Controller.equals(this));
    }

    public abstract void Run();


    public void RunIfCanStart(){
        if(CanStart())
            Run();
    }
}

public class WaitTimeStep extends Step{
    public int time;
    private int ticker = 0;

    public WaitTimeStep(DynObj object, int time){
        super(object);
        this.time = time;
    }

    public void Run(){
        object.Take();
        if(ticker == time){
            object.Release();
        }
        i++;
    }
}

public class MoveStep extends Step{
    public Coord coord = new Coord();

    public MoveStep(DynObj object, Coord coord){
        super(object);
        this.coord = coord;
    }

    public void Run(){
        object.Take();
        object.Move(coord);
        if(object.pos == coord){
            object.StopMovement();
            object.Release();
            isDone = true;
        }
    }
}

public class DetectEnemiesStep extends Step{
    public DetectEnemiesStep(DynObj object, int time){

    }
}
