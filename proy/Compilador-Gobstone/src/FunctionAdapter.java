public class FunctionAdapter {
    Consumer func;

    public void poner(){
        this.func = new Poner();	
    }

    public void sacar(){
        this.func = new Sacar();
    }

    public void mover(){
        this.func = new Mover();
    }

    public void exec(String param){
        this.func.exec(param);
    }
}
