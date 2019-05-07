import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/** Quaternions. Basic operations. */
public class Quaternion {

    // TODO!!! Your fields here!
    //https://introcs.cs.princeton.edu/java/32class/Quaternion.java.html
    /**
     * Constructor from four double values.
     *
     * @param a real part
     * @param b imaginary part i
     * @param c imaginary part j
     * @param d imaginary part k
     */
    private final static double PRECISION = 0.000001;
    private double r;
    private double i, j, k;

    public Quaternion(double a, double b, double c, double d) {
        r = a;
        i = b;
        j = c;
        k = d;

    }

    /**
     * Real part of the quaternion.
     *
     * @return real part
     */
    public double getRpart() {
        return r;
    }

    /**
     * Imaginary part i of the quaternion.
     *
     * @return imaginary part i
     */
    public double getIpart() {
        return i;
    }

    /**
     * Imaginary part j of the quaternion.
     *
     * @return imaginary part j
     */
    public double getJpart() {
        return j;
    }

    /**
     * Imaginary part k of the quaternion.
     *
     * @return imaginary part k
     */
    public double getKpart() {
        return k;
    }

    /**
     * Conversion of the quaternion to the string.
     *
     * @return a string form of this quaternion:
     * "a+bi+cj+dk"
     * (without any brackets)
     */
    @Override
    public String toString() {
        StringBuilder x = new StringBuilder();
        x.append(r);
        if (i < 0) x.append(i).append("i");
        else x.append("+").append(i).append("i");
        if (j < 0) x.append(j).append("j");
        else x.append("+").append(j).append("j");
        if (k < 0) x.append(k).append("k");
        else x.append("+").append(k).append("k");

        return x.toString();

//      return r + "+" + i + "i+" + j + "j+" + k + "k"; //talle ei sobi see...

    }


    /**
     * Conversion from the string to the quaternion.
     * Reverse to <code>toString</code> method.
     *
     * @param s string of form produced by the <code>toString</code> method
     * @return a quaternion represented by string s
     * @throws IllegalArgumentException if string s does not represent
     *                                  a quaternion (defined by the <code>toString</code> method)
     */
    public static Quaternion valueOf(String s) {

        String arg = s.replaceAll("\\P{L}+", "");
        System.out.println(arg);
        for (int i = 0; i < arg.length(); i++)
            for (int j = i + 1; j < arg.length(); j++)
                if (arg.charAt(i) == arg.charAt(j))
                    throw new RuntimeException("Error [" + s + "] Detected duplicate characters.");

//      if (s.matches("[0-9]+"))
//      {
//         System.out.println("tere");
//      }
        LinkedList<String> numbers = new LinkedList<>();

        Pattern p = Pattern.compile("(-?(\\d)+(\\.)?(\\d)*)");
        Matcher m = p.matcher(s);
        while (m.find()){
            numbers.add(m.group());
        }
        if(numbers.size() == 4){
            double r = Double.parseDouble(numbers.get(0));
            double i = Double.parseDouble(numbers.get(1));
            double j = Double.parseDouble(numbers.get(2));
            double k = Double.parseDouble(numbers.get(3));

            System.out.println(numbers);
            return new Quaternion(r, i, j, k);
        } else
            throw new IllegalArgumentException("String " + s + " does not represent a quaternion (a+bi+cj+dk).");


    }


    /**
     * Clone of the quaternion.
     *
     * @return independent clone of <code>this</code>
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Quaternion(r, i, j, k);
    }

    /**
     * Test whether the quaternion is zero.
     *
     * @return true, if the real part and all the imaginary parts are (close to) zero
     */
    public boolean isZero() {
        return equals(new Quaternion(0., 0., 0., 0.));
    }

    /**
     * Conjugate of the quaternion. Expressed by the formula
     * conjugate(a+bi+cj+dk) = a-bi-cj-dk
     *
     * @return conjugate of <code>this</code>
     */
    public Quaternion conjugate() {
        return new Quaternion(r, i * -1., j * -1., k * -1.);
    }

    /**
     * Opposite of the quaternion. Expressed by the formula
     * opposite(a+bi+cj+dk) = -a-bi-cj-dk
     *
     * @return quaternion <code>-this</code>
     */
    public Quaternion opposite() {
        return new Quaternion(r * -1., i * -1., j * -1., k * -1.);
    }

    /**
     * Sum of quaternions. Expressed by the formula
     * (a1+b1i+c1j+d1k) + (a2+b2i+c2j+d2k) = (a1+a2) + (b1+b2)i + (c1+c2)j + (d1+d2)k
     *
     * @param q addend
     * @return quaternion <code>this+q</code>
     */
    public Quaternion plus(Quaternion q) {
        return new Quaternion(r + q.r, i + q.i, j + q.j, k + q.k);
    }

    /**
     * Product of quaternions. Expressed by the formula
     * (a1+b1i+c1j+d1k) * (a2+b2i+c2j+d2k) = (a1a2-b1b2-c1c2-d1d2) + (a1b2+b1a2+c1d2-d1c2)i +
     * (a1c2-b1d2+c1a2+d1b2)j + (a1d2+b1c2-c1b2+d1a2)k
     *
     * @param q factor
     * @return quaternion <code>this*q</code>
     */
    public Quaternion times(Quaternion q) {
        return new Quaternion(
                r * q.r - i * q.i - j * q.j - k * q.k,
                r * q.i + q.r * i + j * q.k - k * q.j,
                r * q.j - i * q.k + j * q.r + k * q.i,
                r * q.k + i * q.j - j * q.i + k * q.r
        );

    }

    /**
     * Multiplication by a coefficient.
     *
     * @param r coefficient
     * @return quaternion <code>this*r</code>
     */
    public Quaternion times(double r) {
        return new Quaternion(this.r * r, i * r, j * r, k * r);
    }

    /**
     * Inverse of the quaternion. Expressed by the formula
     * 1/(a+bi+cj+dk) = a/(a*a+b*b+c*c+d*d) +
     * ((-b)/(a*a+b*b+c*c+d*d))i + ((-c)/(a*a+b*b+c*c+d*d))j + ((-d)/(a*a+b*b+c*c+d*d))k
     *
     * @return quaternion <code>1/this</code>
     */
    public Quaternion inverse() {
        if (isZero())
            throw new RuntimeException("Division by zero is not allowed.");
        return new Quaternion(
                r / (r * r + i * i + j * j + k * k),
                (i * -1.) / (r * r + i * i + j * j + k * k),
                (j * -1.) / (r * r + i * i + j * j + k * k),
                (k * -1.) / (r * r + i * i + j * j + k * k)
        );
    }

    /**
     * Difference of quaternions. Expressed as addition to the opposite.
     *
     * @param q subtrahend
     * @return quaternion <code>this-q</code>
     */
    public Quaternion minus(Quaternion q) {
        return new Quaternion(r - q.r, i - q.i, j - q.j, k - q.k);
    }

    /**
     * Right quotient of quaternions. Expressed as multiplication to the inverse.
     *
     * @param q (right) divisor
     * @return quaternion <code>this*inverse(q)</code>
     */
    public Quaternion divideByRight(Quaternion q) {
        if (isZero())
            throw new RuntimeException("Division by zero is not allowed.");
        return times(q.inverse());


    }

    /**
     * Left quotient of quaternions.
     *
     * @param q (left) divisor
     * @return quaternion <code>inverse(q)*this</code>
     */
    public Quaternion divideByLeft(Quaternion q) {
        if (isZero())
            throw new RuntimeException("Division by zero is not allowed.");
        return q.inverse().times(this);
    }

    /**
     * Equality test of quaternions. Difference of equal numbers
     * is (close to) zero.
     *
     * @param qo second quaternion
     * @return logical value of the expression <code>this.equals(qo)</code>
     */
    @Override
    public boolean equals(Object qo) {
        if (qo instanceof Quaternion)
            return Math.abs(r - ((Quaternion) qo).r) < PRECISION &&
                    Math.abs(i - ((Quaternion) qo).i) < PRECISION &&
                    Math.abs(j - ((Quaternion) qo).j) < PRECISION &&
                    Math.abs(k - ((Quaternion) qo).k) < PRECISION;
        else
            return false;
    }

    /**
     * Dot product of quaternions. (p*conjugate(q) + q*conjugate(p))/2
     *
     * @param q factor
     * @return dot product of this and q
     */
    public Quaternion dotMult(Quaternion q) {
        Quaternion m = times(q.conjugate()).plus(q.times(conjugate()));
        return new Quaternion(m.r / 2., m.i / 2., m.j / 2., m.k / 2.);
    }

    /**
     * Integer hashCode has to be the same for equal objects.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Norm of the quaternion. Expressed by the formula
     * norm(a+bi+cj+dk) = Math.sqrt(a*a+b*b+c*c+d*d)
     *
     * @return norm of <code>this</code> (norm is a real number)
     */
    public double norm() {
        return Math.sqrt(r * r + i * i + j * j + k * k);

    }

    /**
     * Main method for testing purposes.
     *
     * @param arg command line parameters
     */
    public static void main(String[] arg) {

        System.out.println(valueOf("1+1+1+1"));

        Quaternion arv1 = new Quaternion (-1., 1, 2., -2.);
        if (arg.length > 0)
            arv1 = valueOf (arg[0]);
        System.out.println ("first: " + arv1.toString());
        System.out.println ("real: " + arv1.getRpart());
        System.out.println ("imagi: " + arv1.getIpart());
        System.out.println ("imagj: " + arv1.getJpart());
        System.out.println ("imagk: " + arv1.getKpart());
        System.out.println ("isZero: " + arv1.isZero());
        System.out.println ("conjugate: " + arv1.conjugate());
        System.out.println ("opposite: " + arv1.opposite());
        System.out.println ("hashCode: " + arv1.hashCode());
        Quaternion res = null;
        try {
            res = (Quaternion)arv1.clone();
        } catch (CloneNotSupportedException e) {};
        System.out.println ("clone equals to original: " + res.equals (arv1));
        System.out.println ("clone is not the same object: " + (res!=arv1));
        System.out.println ("hashCode: " + res.hashCode());
        res = valueOf (arv1.toString());
        System.out.println ("string conversion equals to original: "
                + res.equals (arv1));
        Quaternion arv2 = new Quaternion (1., -2.,  -1., 2.);
        if (arg.length > 1)
            arv2 = valueOf (arg[1]);
        System.out.println ("second: " + arv2.toString());
        System.out.println ("hashCode: " + arv2.hashCode());
        System.out.println ("equals: " + arv1.equals (arv2));
        res = arv1.plus (arv2);
        System.out.println ("plus: " + res);
        System.out.println ("times: " + arv1.times (arv2));
        System.out.println ("minus: " + arv1.minus (arv2));
        double mm = arv1.norm();
        System.out.println ("norm: " + mm);
        System.out.println ("inverse: " + arv1.inverse());
        System.out.println ("divideByRight: " + arv1.divideByRight (arv2));
        System.out.println ("divideByLeft: " + arv1.divideByLeft (arv2));
        System.out.println ("dotMult: " + arv1.dotMult (arv2));
////   }
    }
}
// end of file
