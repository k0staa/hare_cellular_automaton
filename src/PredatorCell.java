
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JPanel;

public class PredatorCell extends JPanel {

	static boolean[][] gridTab = new boolean[28][17];

	BufferedImage image;

	int positionX;
	int positionY;

	int cellSpeed = 2; // predkosc Cell
	int kierunekMordki; // kierunek twarzy Cell
	int cellLocationX;// = 250; // wspolrzedna X srodka Cell
	int cellLocationY;// = 300; //wspolrzedna Y srodka Cell
	int startAngle = 30;
	int endAngle = 300;
	boolean eatingFace = false; // index uzywany do ruszajacego ryjka
	int lastX = 0, lastY = 0; // ostatnie indexy tabeli do zfalsowania po
								// zrobieniu ruchu;
	int newX, newY; // nowe indexy tabeli ruchu;
	int generatedDirection; // kierunek generowany losowo lub wziety od innego
							// Cella
	boolean following = false;
	Cell followedCell;

	double cellLivingTime; // czas zycia jednej komorki/zajaca
	double cellFollowingTime; // czas podazania komorki za inna
	double timeStep = 0.1;

	public PredatorCell() {
		cellLocationX = this.setRandomX();
		cellLocationY = this.setRandomY();

		this.cellLivingTime = 0;
		this.cellFollowingTime = 0;

	}

	public void drawCell(Graphics g2d) {

		int r = 5; // r is radius of circle

		// drawing a filled Pac Man

		g2d.setColor(Color.RED);

		if (!this.eatingFace) {
			g2d.fillArc(this.cellLocationX - r, this.cellLocationY - r, 2 * r,
					2 * r, startAngle, endAngle);
			this.eatingFace = true;
		} else {
			g2d.fillArc(this.cellLocationX - r, this.cellLocationY - r, 2 * r,
					2 * r, startAngle, 360);
			this.eatingFace = false;
		}

	}

	public void moveCell() {
		Random generator = new Random();
		if (following == true) {
			generatedDirection = followedCell.generatedDirection;
			this.cellFollowingTime += this.timeStep;
		} else {
			generatedDirection = generator.nextInt(10);
			if (generatedDirection >= kierunekMordki + 2
					|| generatedDirection <= kierunekMordki - 2) {
				generatedDirection = kierunekMordki;
			}
			this.cellLivingTime += this.timeStep;

		}

		switch (generatedDirection) {

		case 0:
			this.startAngle = 30;
			this.cellLocationX += cellSpeed;
			this.kierunekMordki = 0;
			break;
		case 1:
			this.startAngle = 75;
			this.cellLocationX += cellSpeed;
			this.cellLocationY -= cellSpeed;
			this.kierunekMordki = 1;
			break;
		case 2:
			this.startAngle = 120;
			this.cellLocationY -= cellSpeed;
			this.kierunekMordki = 2;
			break;
		case 3:
			this.startAngle = 165;
			this.cellLocationX -= cellSpeed;
			this.cellLocationY -= cellSpeed;
			this.kierunekMordki = 3;
			break;
		case 4:
			this.startAngle = 205;
			this.cellLocationX -= cellSpeed;
			this.kierunekMordki = 4;
			break;
		case 5:
			this.startAngle = 250;
			this.cellLocationY += cellSpeed;
			this.cellLocationX -= cellSpeed;
			this.kierunekMordki = 5;
			break;
		case 6:
			this.startAngle = 295;
			this.cellLocationY += cellSpeed;
			this.kierunekMordki = 6;
			break;
		case 7:
			this.startAngle = 340;
			this.cellLocationX += cellSpeed;
			this.cellLocationY += cellSpeed;
			this.kierunekMordki = 7;
			break;
		default:
			switch (this.kierunekMordki) {
			case 0:

				this.cellLocationX += cellSpeed;

				break;
			case 1:

				this.cellLocationX += cellSpeed;
				this.cellLocationY -= cellSpeed;

				break;
			case 2:

				this.cellLocationY -= cellSpeed;

				break;
			case 3:

				this.cellLocationX -= cellSpeed;
				this.cellLocationY -= cellSpeed;

				break;
			case 4:
				this.cellLocationX -= cellSpeed;

				break;
			case 5:

				this.cellLocationY += cellSpeed;
				this.cellLocationX -= cellSpeed;

				break;
			case 6:

				this.cellLocationY += cellSpeed;

				break;
			case 7:

				this.cellLocationX += cellSpeed;
				this.cellLocationY += cellSpeed;

				break;
			}
		}
		if (this.cellLocationX < 210) {
			this.cellLocationX = 1030;
		}
		if (this.cellLocationX > 1030) {
			this.cellLocationX = 210;
		}
		if (this.cellLocationY < 60) {
			this.cellLocationY = 550;
		}
		if (this.cellLocationY > 550) {
			this.cellLocationY = 60;
		}
		this.positionOnGrid();

		// test// System.out.println("X: "+this.cellLocationX + " Y: "+
		// this.cellLocationY);
	}

	public void positionOnGrid() {
		int x = (this.cellLocationX - 200) / 30;
		int y = (this.cellLocationY - 50) / 30;
		if (x != lastX || y != lastY) {
			newX = x;
			newY = y;
			gridTab[x][y] = true;
			gridTab[lastX][lastY] = false;
		}
		// System.out.println("tab[" + x + "]" + "[" + y + "]");
		// TEST System.out.println("LastTab: " + gridTab[lastX][lastY]);
		// System.out.println("NewTab: " + gridTab[x][y]);
		lastX = x;
		lastY = y;

	}

	public int setRandomX() {
		Random generatorX = new Random();
		int x = generatorX.nextInt(1030);
		while (x < 210) {
			x = generatorX.nextInt(1030);
		}

		return x;

	}

	public int setRandomY() {
		Random generatorX = new Random();
		int y = generatorX.nextInt(550);
		while (y < 60) {
			y = generatorX.nextInt(550);
		}

		return y;

	}

	public boolean setToFollow(Cell followedC) {
		if (!followedC.following) {
			this.followedCell = followedC;
			this.following = true;

			return true;
		} else {
			return false;
		}
	}

}
