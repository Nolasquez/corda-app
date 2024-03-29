package bootcamp;

import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* Our state, defining a shared fact on the ledger.
 * See src/main/java/examples/ArtState.java for an example. */
//@BelongsToContract(TokenContract.class)
public class TokenState implements ContractState {
    Party issuer;
    Party owner;
    int amount;
    public TokenState(Party issuer, Party owner, int amount){
        this.issuer = issuer;
        this.owner = owner;
        this.amount = amount;
    }
    public Party getIssuer(){
        return issuer;
    }
    public Party getOwner(){
        return owner;
    }
    public int getAmount(){
        return amount;
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        List<AbstractParty> participants= new ArrayList<>();
        participants.add(issuer);
        participants.add(owner);
        return participants;
    }
}