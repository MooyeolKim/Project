import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class testttt extends Applet implements Runnable, ActionListener {
	public testttt() {
		
		setBounds(0, 0, 450, 300);
		System.out.println("생성자");
	}
	Thread clock;

	Image off; // 메모리 상의 가상화면
	Graphics offG;

	Random r;

	boolean [][] map;
	Color[] colorType;
	Color [][] colorMap;

	final int blockWidth = 30;
	final int blockHeight = 30;
	int blockType;
	int [] blockX;
	int	[] blockY;
	int blockPos;

	int score;
	int delayTime;
	int runGame;

	AudioClip turnAudio;
	AudioClip deleteAudio;
	AudioClip gameOverAudio;

	Button startButton;
	Panel buttonPanel;

	// 메모리 상에 가상화면 만들기
	public void init() {
		this.setSize(1000, 1000);
		System.out.println("init");
		off= createImage(1000, 1000);
		offG= off.getGraphics();
		offG.setColor(Color.white);
		offG.fillRect(0,0,192,192);
		turnAudio= getAudioClip(getCodeBase(), "turn.au");
		deleteAudio= getAudioClip(getCodeBase(), "delete.au");
		gameOverAudio= getAudioClip(getCodeBase(), "gameover.au");
		
		// GUI
		setLayout(new BorderLayout());
		buttonPanel= new Panel();
		
		startButton= new Button("START");
		startButton.addActionListener(this);
		buttonPanel.add(startButton);
		add("South", buttonPanel);
	
		map= new boolean[15][21];
	    colorMap= new Color[15][21];
	    colorType= new Color[7];
	    setColorType();
	
	    blockX= new int[4];
	    blockY= new int[4];
	    blockPos= 0;
	
		r= new Random();
	    blockType= Math.abs(r.nextInt() % 7);
	    setBlockXY(blockType);
	
	    drawBlock();
	    drawMap();
	    drawGrid();
	
	    score= 0;
	    delayTime=1000;
	    runGame= 0;
	
	    addKeyListener(new MyKeyHandler());
	}

	public void setColorType() {
		System.out.println("setColorType");
	    colorType[0]= new Color(65,228,82);
	    colorType[1]= new Color(58,98,235);
	    colorType[2]= new Color(128,0,64);
	    colorType[3]= new Color(255,35,31);
	    colorType[4]= new Color(68,17,111);
	    colorType[5]= new Color(246,118,57);
	    colorType[6]= new Color(224,134,4);
	}

	public void setBlockXY(int type) {
		System.out.println("setBlockXY번");
	    switch(type){
	      case 0:
	        blockX[0]= 5; blockY[0]= 0;
	        blockX[1]= 6; blockY[1]= 0;
	        blockX[2]= 5; blockY[2]= 1;
	        blockX[3]= 6; blockY[3]= 1;
	        break;
	      case 1:
	        blockX[0]= 6; blockY[0]= 0;
	        blockX[1]= 5; blockY[1]= 1;
	        blockX[2]= 6; blockY[2]= 1;
	        blockX[3]= 7; blockY[3]= 1;
	        break;
	      case 2:
	        blockX[0]= 7; blockY[0]= 0;
	        blockX[1]= 5; blockY[1]= 1;
	        blockX[2]= 6; blockY[2]= 1;
	        blockX[3]= 7; blockY[3]= 1;
	        break;
	      case 3:
	        blockX[0]= 5; blockY[0]= 0;
	        blockX[1]= 5; blockY[1]= 1;
	        blockX[2]= 6; blockY[2]= 1;
	        blockX[3]= 7; blockY[3]= 1;
	        break;
	      case 4:
	        blockX[0]= 5; blockY[0]= 0;
	        blockX[1]= 5; blockY[1]= 1;
	        blockX[2]= 6; blockY[2]= 1;
	        blockX[3]= 6; blockY[3]= 2;
	        break;
	      case 5:
	        blockX[0]= 6; blockY[0]= 0;
	        blockX[1]= 5; blockY[1]= 1;
	        blockX[2]= 6; blockY[2]= 1;
	        blockX[3]= 5; blockY[3]= 2;
	        break;
	      case 6:
	        blockX[0]= 4; blockY[0]= 0;
	        blockX[1]= 5; blockY[1]= 0;
	        blockX[2]= 6; blockY[2]= 0;
	        blockX[3]= 7; blockY[3]= 0;
	        break;
	    }
	}

	public void start() {
		System.out.println("start");
	    if(clock==null) {
	      clock= new Thread(this);
	      clock.start();  // 시계 시작
	    }
	}

	public void paint(Graphics g) {
		// 가상화면을 실제화면에 출력
		//System.out.println("5번");
		g.drawImage(off, 40, 40, this); 
	}

	public void update(Graphics g) {
		//System.out.println("6번");
		paint(g);
	}

	public void run() {
		System.out.println("run");
	    while(true) {
	      try{
	        clock.sleep(delayTime);
	      }catch(InterruptedException ie) { }
	
	      dropBlock();
	
	      switch(runGame) {
	      case 1:	  
	        drawBlock();
	        drawMap();
	        drawGrid();
			break;
	      case 2:
	        drawScore();
		    break;
		  default:
			drawTitle();
		    break;
	      }
	      repaint(); // paint() 호출
	    }
	}

	public void drawScore() {
		System.out.println("drawScore");
	    offG.setColor(Color.white);
	    offG.fillRect(150, 120, 110, 70);
	    offG.setColor(Color.black);
	    offG.drawRect(160, 125, 100, 60);
	    offG.setColor(Color.red);
	    offG.drawString("Game Over !", 56, 150);
	    offG.setColor(Color.blue);
	    offG.drawString("Score: "+score, 56, 170);
	}

	public void drawTitle() {
		//System.out.println("9번");
	    offG.setColor(Color.white);
	    offG.fillRect(149, 120, 123, 70);
	    offG.setColor(Color.black);
	    offG.drawRect(151, 125, 121, 60);
	    offG.setColor(Color.red);
	    offG.drawString("TETRIS", 70, 150);
	    offG.setColor(Color.blue);
	    offG.drawString("Press START button!", 35, 170);
	}

	public void dropBlock() {
		//System.out.println("10번");
		removeBlock();

		if(checkDrop()) {
			for(int i=0; i<4; i++)
				blockY[i]= blockY[i]+1;
		}else{
			drawBlock();
			nextBlock();
		}
	}


	public void delLine() {
		System.out.println("delLine");
		boolean delOk;

		for(int row=20; row>=0; row--){
			delOk= true;
			for(int col=0; col<14; col++){
				if(!map[col][row]) delOk= false;
			}

			if(delOk){
				deleteAudio.play();
				score+=10; // 점수 계산

				if(score<1000){  // 속도 조절
					delayTime= 1000 - score;
				}else{
					delayTime=0;
				}

				for(int delRow=row; delRow>0; delRow--){
					for(int delCol=0; delCol<15; delCol++){
						map[delCol][delRow]= map[delCol][delRow-1];
						colorMap[delCol][delRow]= colorMap[delCol][delRow-1];
					}
				}
				for(int i=0; i<15; i++){
					map[0][i]= false;
					colorMap[0][i]= Color.white;
				}
				row++;
			}
		}
	}

	public void nextBlock() {
		System.out.println("nextBlock");
	    blockType= Math.abs(r.nextInt() % 7);
	    blockPos= 0;
	    delLine();
	    setBlockXY(blockType);
	    checkGameOver();
	}

	public void checkGameOver() {
		System.out.println("checkGameOver");
		for(int i=0; i<4; i++){
			if(map[blockX[i]][blockY[i]]){
				if(runGame==1){
					gameOverAudio.play();
					runGame= 2;
				}
			}
		}
	}

	public void removeBlock() {
		//System.out.println("14번");
		for(int i=0; i<4; i++){
			map[blockX[i]][blockY[i]]= false;
			colorMap[blockX[i]][blockY[i]]= Color.white;
		}
	}

	public boolean checkDrop() {
		//System.out.println("15번");
		boolean dropOk= true;
		for(int i=0; i<4; i++){
			if((blockY[i]+1)!=21){
				if(map[blockX[i]][blockY[i]+1]) dropOk= false;
			}else{
				dropOk= false;
			}
		}
		return dropOk;
	}

	public void drawBlock() {
		System.out.println("drawBlock");
		for(int i=0; i<4; i++){
			map[blockX[i]][blockY[i]]= true;
			colorMap[blockX[i]][blockY[i]]= colorType[blockType];
		}
	}

	public void drawMap() {
		System.out.println("drawMap");
		for(int i=0; i<15; i++){
			for(int j=0; j<21; j++){
				if(map[i][j]){
					offG.setColor(colorMap[i][j]);
					offG.fillRect(i*blockHeight, j*blockHeight, blockHeight, blockHeight);
				}else{
					offG.setColor(Color.white);
					offG.fillRect(i*blockHeight, j*blockHeight, blockHeight, blockHeight);
				}
			}
		}
	}

	public void drawGrid() {
		System.out.println("drawGrid");
		offG.setColor(new Color(190,190,190));
		for(int i=0; i<15; i++){
			for(int j=0; j<21; j++){
				offG.drawRect(i*blockHeight, j*blockWidth, blockHeight, blockHeight);
			}
		}
	}

	public void stop() {
		System.out.println("stop");
		if((clock!=null)&&(clock.isAlive())){
			clock=null; // 시계 정지(없앰)
		}
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("actionPerformed");
		blockPos= 0;
		for(int i=0; i<15; i++){
			for(int j=0; j<21; j++){
				map[i][j]= false;
			}
		}

		r= new Random();
	    blockType= Math.abs(r.nextInt() % 7);
	    setBlockXY(blockType);
	
	    drawBlock();
	    drawMap();
	    drawGrid();
	
	    score= 0;
	    delayTime=1000;
	    runGame= 1;
	
		this.requestFocus();
	}

	class MyKeyHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e)
		
		
		{
			int keyCode= (int)e.getKeyCode();

			if(keyCode==KeyEvent.VK_LEFT){
				if(checkMove(-1)){
					for(int i=0; i<4; i++)
						blockX[i]= blockX[i]-1;
				} 
			}

			if(keyCode==KeyEvent.VK_RIGHT){
				if(checkMove(1)){
					for(int i=0; i<4; i++)
						blockX[i]= blockX[i]+1;
				} 
			}

			if(keyCode==KeyEvent.VK_DOWN){
				removeBlock();
				if(checkDrop()){
					for(int i=0; i<4; i++)
						blockY[i]= blockY[i]+1;
				}else{
					drawBlock();
				}
			}
	
			////////////////내가 한거
			if(keyCode==KeyEvent.VK_SPACE){
				removeBlock();
				while(checkDrop()){
					for(int i=0; i<4; i++)
						blockY[i]= blockY[i]+1;
				}
				drawBlock();
				nextBlock();
			}

			if(keyCode==KeyEvent.VK_UP){
		        int[] tempX= new int[4];
		        int[] tempY= new int[4];
		
		        for(int i=0; i<4; i++){
		        	tempX[i]= blockX[i];
		        	tempY[i]= blockY[i];
		        }

		        removeBlock();
		        turnBlock();

		        if(checkTurn()){
		        	turnAudio.play();

		        	if(blockPos<4){
		        		blockPos++;
		        	}else{
		        		blockPos= 0;
		        	}
		        }else{
		        	for(int i=0; i<4; i++){
		        		blockX[i]= tempX[i];
		        		blockY[i]= tempY[i];
		        		map[blockX[i]][blockY[i]]= true;
		        		colorMap[blockX[i]][blockY[i]]= colorType[blockType];
		        	}
		        }
			}

			drawBlock();
			drawMap();
			drawGrid();
			repaint();
		}

		public boolean checkTurn() {
			boolean turnOk= true;

      for(int i=0; i<4; i++){
        if((blockX[i]>=0)&&(blockX[i]<15)&&(blockY[i]>=0)&&(blockY[i]<21)){
          if(map[blockX[i]][blockY[i]]) turnOk= false;
        }else{
          turnOk= false;
        }
      }

      return turnOk;
    }

    public boolean checkMove(int dir) {
    	boolean moveOk= true;
    	removeBlock();

    	for(int i=0; i<4; i++){
    		if(((blockX[i]+dir)>=0)&&((blockX[i]+dir)<15)){
    			if(map[blockX[i]+dir][blockY[i]]) moveOk= false;
    		}else{
    			moveOk= false;
    		}
    	}

    	if(!moveOk) drawBlock();

    	return moveOk;
	}

    public void turnBlock() {
    	switch(blockType){
        case 1:
        	switch(blockPos){
            case 0:
            	blockX[0]= blockX[0]; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]; blockY[1]= blockY[1];
            	blockX[2]= blockX[2]; blockY[2]= blockY[2];
            	blockX[3]= blockX[3]-1; blockY[3]= blockY[3]+1;
            	break;
            case 1:
            	blockX[0]= blockX[0]-1; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]+1; blockY[1]= blockY[1]-1;
            	blockX[2]= blockX[2]+1; blockY[2]= blockY[2]-1;
            	blockX[3]= blockX[3]; blockY[3]= blockY[3]-1;
            	break;
            case 2:
            	blockX[0]= blockX[0]+1; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]; blockY[1]= blockY[1]+1;
            	blockX[2]= blockX[2]; blockY[2]= blockY[2]+1;
            	blockX[3]= blockX[3]; blockY[3]= blockY[3]+1;
            	break;
            case 3:
            	blockX[0]= blockX[0]; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]-1; blockY[1]= blockY[1];
            	blockX[2]= blockX[2]-1; blockY[2]= blockY[2];
            	blockX[3]= blockX[3]+1; blockY[3]= blockY[3]-1;
            	break;
        	}
        	break;
        case 2:
        	switch(blockPos){
            case 0:
            	blockX[0]= blockX[0]-2; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]+1; blockY[1]= blockY[1]-1;
            	blockX[2]= blockX[2]; blockY[2]= blockY[2];
            	blockX[3]= blockX[3]-1; blockY[3]= blockY[3]+1;
            	break;
            case 1:
            	blockX[0]= blockX[0]; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]; blockY[1]= blockY[1];
            	blockX[2]= blockX[2]+1; blockY[2]= blockY[2]-1;
            	blockX[3]= blockX[3]-1; blockY[3]= blockY[3]-1;
            	break;
            case 2:
            	blockX[0]= blockX[0]+1; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]; blockY[1]= blockY[1]+1;
            	blockX[2]= blockX[2]-1; blockY[2]= blockY[2]+2;
            	blockX[3]= blockX[3]+2; blockY[3]= blockY[3]+1;
            	break;
            case 3:
            	blockX[0]= blockX[0]+1; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]-1; blockY[1]= blockY[1];
            	blockX[2]= blockX[2]; blockY[2]= blockY[2]-1;
            	blockX[3]= blockX[3]; blockY[3]= blockY[3]-1;
            	break;
        	}
        	break;
        case 3:
        	switch(blockPos){
            case 0:
            	blockX[0]= blockX[0]+1; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]+1; blockY[1]= blockY[1];
            	blockX[2]= blockX[2]-1; blockY[2]= blockY[2]+1;
            	blockX[3]= blockX[3]-1; blockY[3]= blockY[3]+1;
            	break;
            case 1:
            	blockX[0]= blockX[0]-2; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]-1; blockY[1]= blockY[1]-1;
            	blockX[2]= blockX[2]+1; blockY[2]= blockY[2]-2;
            	blockX[3]= blockX[3]; blockY[3]= blockY[3]-1;
            	break;
            case 2:
            	blockX[0]= blockX[0]+1; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]+1; blockY[1]= blockY[1];
            	blockX[2]= blockX[2]-1; blockY[2]= blockY[2]+1;
            	blockX[3]= blockX[3]-1; blockY[3]= blockY[3]+1;
            	break;
            case 3:
            	blockX[0]= blockX[0]; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]-1; blockY[1]= blockY[1]+1;
            	blockX[2]= blockX[2]+1; blockY[2]= blockY[2];
            	blockX[3]= blockX[3]+2; blockY[3]= blockY[3]-1;
            	break;
        	}
        	break;
        case 4:
        	switch(blockPos){
            case 0:
            case 2:
            	blockX[0]= blockX[0]+1; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]+2; blockY[1]= blockY[1]-1;
            	blockX[2]= blockX[2]-1; blockY[2]= blockY[2];
            	blockX[3]= blockX[3]; blockY[3]= blockY[3]-1;
            	break;
            case 1:
            case 3:
            	blockX[0]= blockX[0]-1; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]-2; blockY[1]= blockY[1]+1;
            	blockX[2]= blockX[2]+1; blockY[2]= blockY[2];
            	blockX[3]= blockX[3]; blockY[3]= blockY[3]+1;
            	break;
        	}
        	break;
        case 5:
        	switch(blockPos){
            case 0:
            case 2:
            	blockX[0]= blockX[0]-1; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]+1; blockY[1]= blockY[1]-1;
            	blockX[2]= blockX[2]; blockY[2]= blockY[2];
            	blockX[3]= blockX[3]+2; blockY[3]= blockY[3]-1;
            	break;
            case 1:
            case 3:
            	blockX[0]= blockX[0]+1; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]-1; blockY[1]= blockY[1]+1;
            	blockX[2]= blockX[2]; blockY[2]= blockY[2];
            	blockX[3]= blockX[3]-2; blockY[3]= blockY[3]+1;
            	break;
        	}
        	break;
        case 6:
        	switch(blockPos){
        	case 0:
            case 2:
            	blockX[0]= blockX[0]+2; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]+1; blockY[1]= blockY[1]+1;
            	blockX[2]= blockX[2]; blockY[2]= blockY[2]+2;
            	blockX[3]= blockX[3]-1; blockY[3]= blockY[3]+3;
            	break;
            case 1:
            case 3:
            	blockX[0]= blockX[0]-2; blockY[0]= blockY[0];
            	blockX[1]= blockX[1]-1; blockY[1]= blockY[1]-1;
            	blockX[2]= blockX[2]; blockY[2]= blockY[2]-2;
            	blockX[3]= blockX[3]+1; blockY[3]= blockY[3]-3;
            	break;
        	}
        	break;
    	}
	}
  }
} 