package bootcamp;

import examples.ArtContract;
import examples.ArtState;
import net.corda.core.contracts.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.LedgerTransaction;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.List;

import static net.corda.core.contracts.ContractsDSL.requireSingleCommand;

/* Our contract, governing how our state will evolve over time.
 * See src/main/java/examples/ArtContract.java for an example. */
public class TokenContract implements Contract{
    public static String ID = "bootcamp.TokenContract";

    @Override
    public void verify(@NotNull LedgerTransaction tx) throws IllegalArgumentException {
        if (tx.getInputStates().size() != 0) throw new IllegalArgumentException ("la transacción debe tener cero input");

        if (tx.getOutputStates().size() != 1) throw new IllegalArgumentException ("la transacción debe tener solo 1 output");

        if (tx.getCommands().size() != 1) throw new IllegalArgumentException ("la transacción debe tener solo 1 command");

        ContractState output = tx.getOutputStates().get(0);
        Command commad = tx.getCommand(0);
        if(!(output instanceof TokenState)) throw new IllegalArgumentException ("la transacción debe tener solo 1 command");
        if(!(commad.getValue() instanceof Commands.Issue)) throw new IllegalArgumentException ("El command debe de ser un Issue");

        TokenState token = (TokenState) output;
        if(token.getAmount() < 0) throw new IllegalArgumentException ("El ammount debe de ser positivo");
        List<PublicKey> requiredSigners = commad.getSigners();
        Party issuer = token.getIssuer();
        PublicKey issuerskey = issuer.getOwningKey();
        if(!(requiredSigners.contains(issuerskey)))  throw new IllegalArgumentException ("Issuer debe ser un signer requerido");


        /*
        List<CommandWithParties<CommandData>> commands = tx.getCommands();
        if (commands.size() != 1) {
            throw new IllegalArgumentException("Must have one command.");
        }

        CommandWithParties<CommandData> command = commands.get(0);
        if (!(command.getValue() instanceof Commands.Issue)) {
            throw new IllegalArgumentException("Command type must be Issue");
        }

        List<ContractState> inputs = tx.getInputStates();
        List<ContractState> outputs = tx.getOutputStates();

        if (inputs.size() != 0) throw new IllegalArgumentException("Must have no inputs.");
        if (outputs.size() != 1) throw new IllegalArgumentException("Must have one output.");

        ContractState output = outputs.get(0);
        if (!(output instanceof TokenState)) {
            throw new IllegalArgumentException("Output must be a token");
        }

        TokenState outputState = (TokenState) output;
        if (outputState.getAmount() < 0) {
            throw new IllegalArgumentException("Purchase price must be positive");
        }

        Party issuer = outputState.getIssuer();
        PublicKey issuerKey = issuer.getOwningKey();
        List<PublicKey> requiredSigners = command.getSigners();
        if (!(requiredSigners.contains(issuerKey))) {
            throw new IllegalArgumentException("Issuer must sign.");
        }
        */
    }


    public interface Commands extends CommandData {
        class Issue implements Commands { }
    }
}