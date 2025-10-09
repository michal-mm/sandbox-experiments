package constructors_in_java25;

import java.util.List;

public class CarGarrage {

    private static void accept(CarInterface car) {
        switch (car) {
            case Volvo v -> v.boast();
            // the line below works, but if not present Tesla falls obviously under Ford
            // case Tesla tt -> tt.boast();
            case Ford f-> {
                    IO.println("Ford can't make it...");
                    f.boast();
            }
            case Toyota t -> t.boast();
            default -> throw new IllegalStateException("Unexpected value: " + car);
        }
    }

    void main() {
        List<CarInterface> cars = List.of(new Ford(), new Volvo(), new Toyota(), new Tesla());

        IO.println("*******");
        cars
                .forEach(car -> {
                    IO.println("-----");
                    accept(car);
                });
    }
}
