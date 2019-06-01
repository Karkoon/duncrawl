package ashlified.graphics.spriterutils;

public enum AnimationID {

  IDLE(0), ATTACK(1), DIE(2), DAMAGED(3);

  private int id;

  AnimationID(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}