package ashlified.util;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class FinalVector3 extends Vector3 {

  public FinalVector3() {
    throw new UnsupportedOperationException();
  }

  public FinalVector3(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public FinalVector3(Vector3 vector) {
    this.x = vector.x;
    this.y = vector.y;
    this.z = vector.z;
  }

  public FinalVector3(float[] values) {
    this.x = values[0];
    this.y = values[1];
    this.z = values[2];
  }

  public FinalVector3(Vector2 vector, float z) {
    this.x = vector.x;
    this.y = vector.y;
    this.z = z;
  }

  @Override
  public Vector3 set(float x, float y, float z) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 set(Vector3 vector) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 set(float[] values) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 set(Vector2 vector, float z) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 setFromSpherical(float azimuthalAngle, float polarAngle) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 setToRandomDirection() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 cpy() {
    return super.cpy();
  }

  @Override
  public Vector3 add(Vector3 vector) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 add(float x, float y, float z) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 add(float values) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 sub(Vector3 a_vec) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 sub(float x, float y, float z) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 sub(float value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 scl(float scalar) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 scl(Vector3 other) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 scl(float vx, float vy, float vz) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 mulAdd(Vector3 vec, float scalar) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 mulAdd(Vector3 vec, Vector3 mulVec) {
    throw new UnsupportedOperationException();
  }

  @Override
  public float len() {
    return super.len();
  }

  @Override
  public float len2() {
    return super.len2();
  }

  @Override
  public boolean idt(Vector3 vector) {
    return super.idt(vector);
  }

  @Override
  public float dst(Vector3 vector) {
    return super.dst(vector);
  }

  @Override
  public float dst(float x, float y, float z) {
    return super.dst(x, y, z);
  }

  @Override
  public float dst2(Vector3 point) {
    return super.dst2(point);
  }

  @Override
  public float dst2(float x, float y, float z) {
    return super.dst2(x, y, z);
  }

  @Override
  public Vector3 nor() {
    throw new UnsupportedOperationException();
  }

  @Override
  public float dot(Vector3 vector) {
    return super.dot(vector);
  }

  @Override
  public float dot(float x, float y, float z) {
    return super.dot(x, y, z);
  }

  @Override
  public Vector3 crs(Vector3 vector) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 crs(float x, float y, float z) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 mul4x3(float[] matrix) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 mul(Matrix4 matrix) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 traMul(Matrix4 matrix) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 mul(Matrix3 matrix) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 traMul(Matrix3 matrix) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 mul(Quaternion quat) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 prj(Matrix4 matrix) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 rot(Matrix4 matrix) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 unrotate(Matrix4 matrix) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 untransform(Matrix4 matrix) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 rotate(float degrees, float axisX, float axisY, float axisZ) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 rotateRad(float radians, float axisX, float axisY, float axisZ) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 rotate(Vector3 axis, float degrees) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 rotateRad(Vector3 axis, float radians) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isUnit() {
    return super.isUnit();
  }

  @Override
  public boolean isUnit(float margin) {
    return super.isUnit(margin);
  }

  @Override
  public boolean isZero() {
    return super.isZero();
  }

  @Override
  public boolean isZero(float margin) {
    return super.isZero(margin);
  }

  @Override
  public boolean isOnLine(Vector3 other, float epsilon) {
    return super.isOnLine(other, epsilon);
  }

  @Override
  public boolean isOnLine(Vector3 other) {
    return super.isOnLine(other);
  }

  @Override
  public boolean isCollinear(Vector3 other, float epsilon) {
    return super.isCollinear(other, epsilon);
  }

  @Override
  public boolean isCollinear(Vector3 other) {
    return super.isCollinear(other);
  }

  @Override
  public boolean isCollinearOpposite(Vector3 other, float epsilon) {
    return super.isCollinearOpposite(other, epsilon);
  }

  @Override
  public boolean isCollinearOpposite(Vector3 other) {
    return super.isCollinearOpposite(other);
  }

  @Override
  public boolean isPerpendicular(Vector3 vector) {
    return super.isPerpendicular(vector);
  }

  @Override
  public boolean isPerpendicular(Vector3 vector, float epsilon) {
    return super.isPerpendicular(vector, epsilon);
  }

  @Override
  public boolean hasSameDirection(Vector3 vector) {
    return super.hasSameDirection(vector);
  }

  @Override
  public boolean hasOppositeDirection(Vector3 vector) {
    return super.hasOppositeDirection(vector);
  }

  @Override
  public Vector3 lerp(Vector3 target, float alpha) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 interpolate(Vector3 target, float alpha, Interpolation interpolator) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 slerp(Vector3 target, float alpha) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public Vector3 fromString(String v) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 limit(float limit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 limit2(float limit2) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 setLength(float len) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 setLength2(float len2) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Vector3 clamp(float min, float max) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public boolean epsilonEquals(Vector3 other, float epsilon) {
    return super.epsilonEquals(other, epsilon);
  }

  @Override
  public boolean epsilonEquals(float x, float y, float z, float epsilon) {
    return super.epsilonEquals(x, y, z, epsilon);
  }

  @Override
  public Vector3 setZero() {
    throw new UnsupportedOperationException();
  }
}
