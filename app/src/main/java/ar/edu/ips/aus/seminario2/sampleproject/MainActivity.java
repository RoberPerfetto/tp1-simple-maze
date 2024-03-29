package ar.edu.ips.aus.seminario2.sampleproject;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonUp, buttonDown, buttonLeft, buttonRight;

    ImageView[] imageViews = null;

    private MazeBoard board;
    private String user_name;
    private String maze_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maze);

        Intent intent = getIntent();
        user_name = intent.getStringExtra(MenuActivity.EXTRA_MESSAGE);
        maze_option = intent.getStringExtra(MenuActivity.BUTTON_MESSAGE);

        TextView textView = findViewById(R.id.playerName);
        textView.setText(user_name);

        buttonUp = findViewById(R.id.buttonUp);
        buttonDown = findViewById(R.id.buttonDown);
        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);

        buttonUp.setOnClickListener(this);
        buttonDown.setOnClickListener(this);
        buttonLeft.setOnClickListener(this);
        buttonRight.setOnClickListener(this);

        GameView mazeView = (GameView)findViewById(R.id.gameView);

        mazeView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        mazeView.setMaze_option(maze_option);
        mazeView.init();
        mazeView.setZOrderMediaOverlay(true);
        mazeView.setZOrderOnTop(true);

        setupMazeBoard(mazeView.getBoard());
    }

    // TODO this must probably be moved to GameView, or helper.
    private void setupMazeBoard(MazeBoard theBoard) {

        board = theBoard;
        int height = board.getHeight();
        int width = board.getWidth();

        imageViews = new ImageView[width * height];

        int resId = 0;

        TableLayout table = findViewById(R.id.mazeBoard);
        for (int i=0; i<height; i++){
            TableRow row = new TableRow(this);
            TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams();
            rowParams.width = TableRow.LayoutParams.MATCH_PARENT;
            rowParams.height = TableRow.LayoutParams.MATCH_PARENT;
            rowParams.weight = 1;
            rowParams.gravity = Gravity.CENTER;
            row.setLayoutParams(rowParams);
            table.addView(row);

            for (int j=0; j<width; j++){
                BoardPiece piece = board.getPiece(j, i);

                resId = lookupResource(piece);

                ImageView imageView = new ImageView(this);
                imageView.setBackgroundResource(resId);
                TableRow.LayoutParams imageViewParams = new TableRow.LayoutParams();
                imageViewParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                imageViewParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                imageViewParams.weight = 1;
                imageView.setLayoutParams(imageViewParams);

                row.addView(imageView);

                imageViews[(j%board.getWidth())+ board.getHeight()*i] = imageView;
            }
        }
   }

    private int lookupResource(BoardPiece piece) {
        if(piece.getExit()){
         return R.drawable.m5;
        }else {
            int iconIndex = 0b1000 * (piece.isOpen(MazeBoard.Direction.WEST) ? 1 : 0) +
                    0b0100 * (piece.isOpen(MazeBoard.Direction.NORTH) ? 1 : 0) +
                    0b0010 * (piece.isOpen(MazeBoard.Direction.EAST) ? 1 : 0) +
                    0b0001 * (piece.isOpen(MazeBoard.Direction.SOUTH) ? 1 : 0);

            int[] iconLookupTable = {
                    0,
                    R.drawable.m1b,
                    R.drawable.m1r,
                    R.drawable.m2rb,
                    R.drawable.m1t,
                    R.drawable.m2v,
                    R.drawable.m2tr,
                    R.drawable.m3l,
                    R.drawable.m1l,
                    R.drawable.m2bl,
                    R.drawable.m2h,
                    R.drawable.m3t,
                    R.drawable.m2lt,
                    R.drawable.m3r,
                    R.drawable.m3b,
                    R.drawable.m4};

            return iconLookupTable[iconIndex];
        }
    }

    @Override
    public void onClick(View v) {
        // complete with event listener logic
        if (v == buttonUp) {
            board.setNewDirection(MazeBoard.Direction.NORTH);
        }
        else if (v == buttonDown) {
            board.setNewDirection(MazeBoard.Direction.SOUTH);
        }
        else if (v == buttonLeft) {
            board.setNewDirection(MazeBoard.Direction.WEST);
        }
        else if (v == buttonRight) {
            board.setNewDirection(MazeBoard.Direction.EAST);
        }

    }

    public String getMaze_option(){
        return maze_option;
    }
}
