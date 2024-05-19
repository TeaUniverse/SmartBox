package smartbox.components;


import java.util.*;
import smartbox.*;

public class StatsCalculator extends Component implements App {

    // field needs to be public for setProvider(Class<?> intf, Component provider) in Component class
    public ICalculator arithmeticCalculator;

    public StatsCalculator() {
        super();
    }

    // The required interface "arithmeticCalculator" of this component can only be initialized (In other words,
    // the function mean() only works) when the container connects it to an interface provided by another
    // component called Calculator.
    public Double mean(List<Double> data) throws Exception {
        Double sum = 0.0;
        for(Double val: data) {
            sum = arithmeticCalculator.add(sum, val);
        }
        Double avg = arithmeticCalculator.div(sum, (double)data.size());
        return avg;
    }


    public void main() throws Exception {

        List<Double> scores = new LinkedList<Double>();
        for(int i = 0; i < 100; i++) {
            scores.add((double)i);
        }
        Double avg = mean(scores);
        mvc.Utilities.inform("Average = " + avg);
    }

}
