import java.security.SecureRandom;
import java.math.BigInteger;

public final class IdentifierGenerator {
	private SecureRandom random = new SecureRandom();

	public String nextId() {
		return new BigInteger(130, random).toString(32);
	}
}