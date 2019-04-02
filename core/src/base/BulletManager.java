package base;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer.Random;
import com.groupproject.util.GameClientService;

public class BulletManager {

	private ArrayList<Bullet> mMyBullets;
	private ArrayList<Bullet> mOtherBullets;
	public Date mLastBulletRecievedDate;
	private BulletService mBS;

	public BulletManager(GameClientService gcs) {
		mMyBullets = new ArrayList<>();
		mOtherBullets = new ArrayList<>();
		mLastBulletRecievedDate = new Date();
		mBS = new BulletService(gcs);
	}

	public void update(Player mainPlayer, Player[] otherPlayers) {

		for (int index = mMyBullets.size() - 1; index >= 0; index--) {
			if (!mMyBullets.get(index).Update()) {
				mMyBullets.remove(index);
				// removeMyBullets.add(mMyBullets.get(index));
			} else {
				for (int playerIndex = otherPlayers.length - 1; playerIndex >= 0; playerIndex--) {
					if (null != otherPlayers[playerIndex]) {
						otherPlayers[playerIndex].calculateTilePos();
						if (!mMyBullets.isEmpty() && null != mMyBullets.get(index)
								&& mMyBullets.get(index).collisionCheck(otherPlayers[playerIndex])) {
							mMyBullets.remove(index);
							
						}
					}
				}
			}
		}
		for (int index = mOtherBullets.size() - 1; index >= 0; index--) {
			if (!mOtherBullets.get(index).Update()) {
				mOtherBullets.remove(index);
				// removeMyBullets.add(mMyBullets.get(index));
			} else {
				if (mOtherBullets.get(index).collisionCheck(mainPlayer)) {
					mOtherBullets.remove(index);
					mainPlayer.getPosition().setX(960);
					mainPlayer.getPosition().setY(540);
					
				}
			}
		}
		// for (Bullet bullet : mOtherBullets) {
		// if (!bullet.Update()) {
		// removeOtherBullets.add(bullet);
		// }
		// }
		//
		// mOtherBullets.removeAll(removeOtherBullets);
	}

	synchronized public void draw(SpriteBatch sb) {
		for (Bullet bullet : mMyBullets) {
			bullet.Draw(sb);

		}

		for (Bullet bullet : mOtherBullets) {
			bullet.Draw(sb);
		}
	}

	public JSONArray getMyBulletsToString() {
		JSONArray allBullets = new JSONArray();
		for (Bullet bullet : mMyBullets) {
			allBullets.put(bullet.toJSON());
		}
		return allBullets;
	}

	synchronized public JSONArray getOtherBulletsToString() {
		JSONArray allBullets = new JSONArray();
		for (Bullet bullet : mOtherBullets) {
			allBullets.put(bullet.toJSON());
		}
		return allBullets;
	}

	public void addToMyBullet(Bullet newBullet) {
		mMyBullets.add(newBullet);
		mBS.uploadBullet(newBullet);
	}

	/**
	 * Don't forget to start drawing the bullets asap.
	 **/
	synchronized public void addToOthersBullets(JSONArray othersBullets) {
		for (int index = 0; index < othersBullets.length(); index++) {
			// Create bullet from JSON
			Bullet incomingBullet = new Bullet();
			incomingBullet.toBullet((JSONObject) othersBullets.get(index));

			// Check if bullet not in the list already, then add it.
			if (!mOtherBullets.contains(incomingBullet)) {
				mOtherBullets.add(incomingBullet);
			}

			// Check if the date of this new bullet is newer than my current date. if yes,
			// then change to the new date.
			if (incomingBullet.getCreationDate().after(mLastBulletRecievedDate))
				mLastBulletRecievedDate = incomingBullet.getCreationDate();
		}
	}
}
