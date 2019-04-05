package base;

import org.json.JSONArray;

import com.groupproject.util.GameClientService;
import com.groupproject.util.MessageType;

/**
 * @author Mohammed Al-Safwan
 * @date Mar 29, 2019
 */
public class BulletService {

	private GameClientService mGCS;

	public BulletService(GameClientService gcs) {
		this.mGCS = gcs;
	}

	public void uploadBullet(Bullet newBullet) {
		Message bulletMsg = new Message();
		bulletMsg.setType(MessageType.SEND_BULLET);
		bulletMsg.setSender(newBullet.getReference() + "");
		bulletMsg.setBody(newBullet.toString());
		mGCS.sendRequest(bulletMsg.toString());
	}

	public void bulletKilledPlayer(int playerID, int bulletOwnerID) {
		Message killerBulletMsg = new Message();
		killerBulletMsg.setType(MessageType.KILLER_BULLET);
		killerBulletMsg.setSender(playerID + "");
		killerBulletMsg.setBody(bulletOwnerID + "");
		mGCS.sendRequest(killerBulletMsg.toString());
	}
}
