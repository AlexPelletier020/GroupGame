package base;

import java.util.Date;

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

	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	// Method				:	void uploadBullet()
	//
	// Method parameters	:	args - the method permits String command line parameters to be entered
	//
	// Method return		:	void
	//
	// Synopsis				:   
	//							
	//
	// Modifications		:
	//							Date			    Developer				Notes
	//							  ----			      ---------			 	     -----
	//							Mar 29, 2019		    Mohammed Al-Safwan				Initial setup
	//
	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	public void uploadBullet(Bullet newBullet) {
		Message bulletMsg = new Message();
		bulletMsg.setType(MessageType.SEND_BULLET);
		bulletMsg.setSender(newBullet.getReference() + "");
		bulletMsg.setBody(newBullet.toString());
		mGCS.sendRequest(bulletMsg.toString());
	}

}
