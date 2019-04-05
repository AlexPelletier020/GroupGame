package base;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupproject.game.Logic;
import com.groupproject.util.GameClientService;
import com.groupproject.util.TileType;

public class BulletManager {

	private ArrayList<Bullet> mMyBullets;
	private ArrayList<Bullet> mOtherBullets;
	public Date mLastBulletRecievedDate;
	private BulletService mBS;
	private Random killme;

	public BulletManager(GameClientService gcs) {
		mMyBullets = new ArrayList<>();
		mOtherBullets = new ArrayList<>();
		mLastBulletRecievedDate = new Date();
		mBS = new BulletService(gcs);
		killme = new Random();
		killme.setSeed(new Date().getTime());
	}

	public void update(Player mainPlayer, Player[] otherPlayers) {

		for (int index = mMyBullets.size() - 1; index >= 0; index--) {
			if (!mMyBullets.get(index).Update()) {
				//Remove bullet
				mMyBullets.remove(index);
			} else {
				for (int playerIndex = otherPlayers.length - 1; playerIndex >= 0; playerIndex--) {
					if (null != otherPlayers[playerIndex]) {
						otherPlayers[playerIndex].calculateTilePos();
						if (!mMyBullets.isEmpty() && null != mMyBullets.get(index)
								&& mMyBullets.get(index).collisionCheck(otherPlayers[playerIndex])) {
							//Remove bullet
							mMyBullets.remove(index);
						}
					}
				}
			}
		}

		for (int index = mOtherBullets.size() - 1; index >= 0; index--) {
			if (!mOtherBullets.get(index).Update()) {
				mOtherBullets.remove(index);
			} else {
				if (mOtherBullets.get(index).collisionCheck(mainPlayer)) {
					//Send player died
					mBS.bulletKilledPlayer(mainPlayer.getId(), mOtherBullets.get(index).getReference());
					//Remove bullet
					mOtherBullets.remove(index);
					//Spawn player
					float xPos = 0;
					float yPos = 0;
					do {
						xPos = (killme.nextFloat() * 48.0f * Logic.mMap.length);
						yPos = (killme.nextFloat() * 48.0f * Logic.mMap[0].length);
					} while ((Logic.mMap[(int) (xPos / 48)][(int) (yPos / 48)].getTileType() == TileType.WALL));

					mainPlayer.getPosition().setX(xPos - 24);
					mainPlayer.getPosition().setY(yPos - 24);

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
