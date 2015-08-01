/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 *
 * @author kostek
 */
public class Grid extends JPanel implements ActionListener {

	BufferedImage image;
	Timer timer;
	int delayForTime;
	ArrayList<Cell> myCells;
	ArrayList<PredatorCell> myPreds;
	int paddingSpace;
	int x_size;
	int y_size;
	boolean isStarted = false;
	double livingTime;
	double timeStep = 0.1;
	double tempTime; // na potrzeby funkcji checkresources
	double resources;
	JTextArea infoArea;
	boolean enoughResources = true;

	public Grid(int x_size, int y_size, int padding_space, BufferedImage img) {
		this.paddingSpace = padding_space;
		this.x_size = x_size;
		this.y_size = y_size;
		this.image = img;
		// Resources to zarcie dla zajecy
		this.resources = 10;
		// Inicjalizacja JTextArea -bedzie to panel informujacy o ilosci
		// poszczegolnych elementow symulacji
		infoArea = new JTextArea();

		// Timer i czas zycia stworzen
		delayForTime = 30;
		timer = new Timer(delayForTime, this);
		livingTime = 0;

	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = this.image.createGraphics();
		g.drawImage(image, 200, 50, this);

		drawGrid(g2d);
		if (isStarted) {
			for (Cell myCell : myCells) {
				myCell.drawCell(g);
			}
			for (PredatorCell myPred : myPreds) {
				myPred.drawCell(g);
			}
		}
		g2d.dispose();

	}

	// Siatka rzutu
	private void drawGrid(Graphics g) {
		image.createGraphics();
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		for (int i = 0; i < (StrictMath.max(x_size, y_size) - paddingSpace); i++) {
			g2d.setColor(new Color(022, 053, 055));

			g2d.drawLine((i * 20) + paddingSpace, y_size - paddingSpace,
					(i * 20) + paddingSpace, 0);
			g2d.drawLine(paddingSpace, y_size - (i * 20 + paddingSpace),
					x_size, y_size - (i * 20 + paddingSpace));
			g2d.setColor(new Color(255, 255, 0));

		}

	}

	public void startAnim(int amountCells, int amountPredators,
			int amountResources) {
		// Tworzymy i inicjalizujemy komorki Cell
		if (!isStarted) {
			myCells = new<Cell> ArrayList();
			for (int i = 0; i < amountCells; i++) {
				myCells.add(new Cell(90));

			}
			this.resources = amountResources;
			isStarted = true;
		}
		myPreds = new<PredatorCell> ArrayList();
		for (int i = 0; i < amountPredators; i++) {
			myPreds.add(new PredatorCell());

		}

		timer.start();
	}

	public void stopAnim() {
		timer.stop();
		// ///Test - Polozaenie komorki Cell////////////
		// for (int k = 0; k < 28; k++) {
		// for (int i = 0; i < 17; i++) {
		// System.out.println("tab[" + k + "][" + i + "]  " +
		// Cell.gridTab[k][i]);
		//
		// }
		// }
		// ///Test - Podazanie za komorka////////////
		for (int i = 0; i < myCells.size(); i++) {
			System.out.println("Komorka:" + i + "podaza: "
					+ myCells.get(i).following);
		}
		System.out.println("Ilosc podazajacych: " + Cell.followingCells);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		for (Cell myCell : myCells) {
			myCell.moveCell();

		}
		for (PredatorCell myPred : myPreds) {
			myPred.moveCell();
		}
		this.repaint();
		this.setCellToFollow();

		this.addCellAfterFollowing();
		this.livingTime += this.timeStep;
		this.checkResources(this.livingTime);
		this.checkBirth();
		this.setPredatorKill();
		this.checkIfTimeToDie();

		this.refreshTextArea();

	}

	public void setCellToFollow() {
		if (Cell.followingCells < myCells.size() - 1) {
			for (int i = 0; i < myCells.size(); i++) {
				for (int j = 1; j < myCells.size(); j++) {
					if (myCells.get(i).gender != myCells.get(j).gender) {
						if (myCells.get(i).following == false
								&& myCells.get(j).following == false) {
							int differenceX = myCells.get(i).cellLocationX
									- myCells.get(j).cellLocationX;
							int differenceY = myCells.get(i).cellLocationY
									- myCells.get(j).cellLocationY;
							if ((differenceX >= -10 && differenceX <= 10)
									&& (differenceY >= -10 && differenceY <= 10)) {
								myCells.get(i).setToFollow(myCells.get(j));

							}
						}
					}
				}

			}
		}

	}

	public void setPredatorKill() {

		if (myCells.size() > 0) {
			for (int i = 0; i < myPreds.size(); i++) {
				for (int j = 0; j < myCells.size(); j++) {

					int differenceX = myPreds.get(i).cellLocationX
							- myCells.get(j).cellLocationX;
					int differenceY = myPreds.get(i).cellLocationY
							- myCells.get(j).cellLocationY;
					if ((differenceX >= -3 && differenceX <= 3)
							&& (differenceY >= -3 && differenceY <= 3)) {

						removeCell(j);
						System.out.println("KILL!!");
					}

				}

			}
		}
	}

	public void refreshTextArea() {
		int ilSamcow = 0, ilSamic = 0;
		for (int i = 0; i < myCells.size(); i++) {
			if (myCells.get(i).gender == 1) {
				ilSamcow += 1;
			} else {
				ilSamic += 1;
			}

		}
		infoArea.setText("Czas zycia zwierzat: " + this.livingTime + "dni\n");
		infoArea.append("Ilosc samcow zajecy: " + ilSamcow + "szt\n");
		infoArea.append("Ilosc samic zajecy: " + ilSamic + "szt\n");
		infoArea.append("Ilosc wilkow: " + (myPreds.size()) + "szt\n");
		infoArea.append("Ilosc zasobow: " + this.resources + "szt\n");
	}

	public JTextArea getTextArea() {

		return infoArea;
	}

	public void addCellAfterFollowing() {
		for (int i = 0; i < myCells.size(); i++) {
			if (myCells.get(i) != null) {
				if (myCells.get(i).cellFollowingTime > 1.0) {

					if (myCells.get(i).getIfReadyToReprod()
							|| myCells.get(i).followedCell.getIfReadyToReprod()) {
						myCells.get(i).following = false;
						myCells.get(i).cellFollowingTime = 0;
						myCells.get(i).followedCell.following = false;
						myCells.get(i).followedCell.cellFollowingTime = 0;

					}
				}
			}
		}
	}

	public void checkResources(double time) {
		this.checkIfResourceDieTme(time, tempTime);
		if (time > tempTime + 10) {
			this.resources += 0.2;
			if (this.resources > 0) {
				for (Cell myCell : myCells) {
					if (this.resources >= 0) {
						this.resources -= myCell.howManyResourcesNeed;
					}
				}
			}
			tempTime = time;

		}

	}

	public void checkBirth() {
		Random randomGen = new Random();

		int babyCells = 0;
		for (Cell myCell : myCells) {
			if (myCell.pregnanttime > 45) {
				babyCells += 1;
				myCell.pregnanttime = 0;
				myCell.pregnant = false;
			}

		}
		if (this.enoughResources) {
			if (babyCells > 0) {
				for (int i = 1; i <= babyCells; i++) {
					for (int j = 0; j < randomGen.nextInt(5); j++) {

						myCells.add(new Cell());
					}

				}
			}
		}
	}

	public void checkIfResourceDieTme(double time, double tempT) {
		Random randGen = new Random();

		if (this.resources < 2) {
			this.enoughResources = false;
			if (time > tempT + 10) {
				int cellsToKill = randGen.nextInt(2);
				if (cellsToKill > 0) {

					for (int i = 1; i <= cellsToKill; i++) {
						int tempIndex = randGen.nextInt(myCells.size() - 1);

						removeCell(tempIndex);

						System.out.println("Killing time!!!!!!!!!!!!!");
					}
				}

			}
		} else {
			this.enoughResources = true;
		}

	}

	public void checkIfTimeToDie() {
		for (int i = 0; i < myCells.size(); i++) {

			if (myCells.get(i).cellLivingTime > 3745) {
				this.removeCell(i);
			}
		}
	}

	public void removeCell(int index) {
		if (index < myCells.size()) {
			for (Cell myCell : myCells) {
				if (myCell.followedCell == myCells.get(index)) {
					myCell.followedCell = myCell;
					myCell.following = false;
				}
			}
			myCells.remove(index);

		}
	}

}
