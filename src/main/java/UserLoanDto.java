import com.example.Gruppuppgift_Statsbibloteket.model.Loan;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UserLoanDto {
    private String firstName;
    private String lastName;
    private Loan loan;

}