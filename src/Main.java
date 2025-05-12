import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
class Question {
    String name;
    String description;
    String type;
    boolean required;
    BufferedReader in;
    Question(
            String name,
            String description,
            String type,
            boolean required
    ){
        this.name = name;
        this.description = description;
        this.type = type;
        this.required = required;
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }
    private void displayQuestion(){
        System.out.printf("%s - (%s): ", name, description);
    }
    boolean isValid(String draftValue){
        if(this.type == "Decimal") {
            try {
                Double d = Double.parseDouble(draftValue);
            }catch (NumberFormatException e){
                return false;
            }
        }
        return required && !draftValue.isBlank();
    }
    Answer getAnswer(){
        String draftValue = null;
        try {
            do {
                this.displayQuestion();
                draftValue = in.readLine();
            } while (!isValid(draftValue));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(this.type == "Decimal"){
            return new DecimalAnswer(this, Double.parseDouble(draftValue));
        }
        return new Answer(this, draftValue);
    }
}
class Interaction{
    ArrayList<Question> list;
    Interaction(){
        list = new ArrayList<>();
    }
    public void add(Question field){
        list.add(field);
    }
    public ArrayList<Answer> display() {
        ArrayList<Answer> answers = new ArrayList<>();
        for(Question f : list){
            answers.add(f.getAnswer());
        }
        return answers;
    }
}
class Answer{
    Question question;
    String value;
    Answer(Question question, String value){
        this.question = question;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
class DecimalAnswer extends Answer{
    Double value;
    DecimalAnswer(Question question, Double value) {
        super(question, value.toString());
        this.question = question;
        this.value = value;
    }
    @Override
    public String toString() {
        return this.value.toString();
    }
}
public class Main {
    public static void main(String[] args) {
        Interaction interaction = new Interaction();
        interaction.add(new Question("Color","Primary base color of the car.", "String", true));
        interaction.add(new Question("Price","Asking price from the dealer.", "Decimal", true));

        ArrayList<Answer> answers = interaction.display();

        for(Answer a : answers){
            System.out.println(a);
        }
    }
}
