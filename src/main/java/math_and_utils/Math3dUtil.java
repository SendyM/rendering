package math_and_utils;
import static java.lang.Math.sqrt;

/**
 * Class containing Mainly Math for 3D graphics
 * @author rasto
 */
public class Math3dUtil {
    public static final double epsilon = 1e-8;
    
    //https://stackoverflow.com/questions/11132681/what-is-a-formula-to-get-a-vector-perpendicular-to-another-vector
    /**
     * It isn't normalized
     * @param v
     * @return 
     */
    public static double[] getPerpendicularVector(double[] v){
        double r[] = new double[3];
        
        r[0] = r[1] = 1;
        if(v[2] == 0) {return r;}
        r[2] = ( (-v[0])*r[0] - v[1]*r[1] )/v[2];
        return r;
    }
    
    /**
     * cos(f) = (a*b)/(||a||*||b||) 
     * @param a
     * @param b
     * @return acos(cos(f)) * (double)(180/Math.PI) -> angle in degrees
     */
    public static double angleBetvenVectors(double[] a, double[] b){
        double axb[] = new double[3];
        axb[0] = a[0]*b[0];
        axb[1] = a[1]*b[1];
        axb[2] = a[2]*b[2];
        return (double)Math.acos( (axb[0]+axb[1]+axb[2])/
                ( (double)Math.sqrt(a[0]*a[0]+a[1]*a[1]+a[2]*a[2])*
                  (double)Math.sqrt(b[0]*b[0]+b[1]*b[1]+b[2]*b[2]) )) * 
                (double)(180/Math.PI);
    }
    /**
     * https://stackoverflow.com/questions/31225062/rotating-a-vector-by-angle-and-axis-in-java
     * @param vec vector to rotate
     * @param axis rotate around
     * @param theta counterclockwise
     * @return 
     */
    public static Vector3 rotateVectorCC(Vector3 vec, Vector3 axis, double theta){
    double x, y, z;
    double u, v, w;
    x=vec.x;y=vec.y;z=vec.z;
    u=axis.x;v=axis.y;w=axis.z;
    double xPrime = u*(u*x + v*y + w*z)*(1d - Math.cos(theta)) 
            + x*Math.cos(theta)
            + (-w*y + v*z)*Math.sin(theta);
    double yPrime = v*(u*x + v*y + w*z)*(1d - Math.cos(theta))
            + y*Math.cos(theta)
            + (w*x - u*z)*Math.sin(theta);
    double zPrime = w*(u*x + v*y + w*z)*(1d - Math.cos(theta))
            + z*Math.cos(theta)
            + (-v*x + u*y)*Math.sin(theta);
    return new Vector3(xPrime, yPrime, zPrime);
}
    
    // |v|
    public static double vectorLenght(double[] v){
        return (double)sqrt((v[0] * v[0]) + (v[1] * v[1]) + (v[2] * v[2]));
    }
    
    //http://www.fundza.com/vectors/normalize/
    public static double[] normalizeVector(double[] v){
        double r[] = new double[3];
        double l = vectorLenght(v);
        r[0] = v[0] /l;
        r[1] = v[1] /l;
        r[2] = v[2] /l;
        return r;
    }
    
    public static double wavelenghtEnergy(double l){
        //nm to m
        double lm = l*(1.0/1000000000.0);
        //E = hc/lm
        return (6.626 * Math.pow(10, -34) * 3.0* Math.pow(10,8))/lm;
    }
    
    //Lx is angle in XY pane, from X counterclockwise
    //Ly is angle in YZ pane, from Y counterclockwise
    public static double[] beamArrayToVector(double v[]){
        double[] r = new double[3];
        
        r[0] = Math.sin((double) v[3]);     //sin
        r[1] = Math.cos((double) v[3]);     //cos
        r[2] = Math.sin((double) v[4]);     //y is cos, Z is sin
        
        return r;
    }
    
    public static boolean vithinError(double a, double b, double e){
        if(a > b-e && a < b+e){
            return true;
        }
        return false;
    }
    
    public static double fitinterval(double numb, double a, double b){
        if(numb < a){return a;}
        if(numb >b){return b;}
        return numb;
    }
    
    //TBD
    //https://www.cprogramming.com/tutorial/3d/rotation.html
    /**
     * https://stackoverflow.com/questions/31225062/rotating-a-vector-by-angle-and-axis-in-java
     * counter clockwise around unit vector 
     * @param v
     * @param p
     * @param angle
     * @return 
     */
    public static double[] rotatePointAroundVector(double[] vec, double[] p, double theta){
        double [] r = new double[3];
        double x = p[0], y = p[1], z = p[2], u = vec[0], v = vec[1], w = vec[2];
        
        r[0] = u*(u*x + v*y + w*z)*(1d - Math.cos(theta)) 
                + x*Math.cos(theta)
                + (-w*y + v*z)*
                Math.sin(theta);
        r[1] = v*(u*x + v*y + w*z)*(1d - Math.cos(theta))
                + y*Math.cos(theta)
                + (w*x - u*z)*Math.sin(theta);
        r[2] = w*(u*x + v*y + w*z)*(1d - Math.cos(theta))
                + z*Math.cos(theta)
                + (-v*x + u*y)*Math.sin(theta);
        
        return r;
    }
    
    public static double getPositiveAngle(double a){
        return ((360 + a) % 360);
    }
    
    
    
    /*------------------------------------------------------------------*/
    /**https://introcs.cs.princeton.edu/java/22library/Matrix.java.html**/
    //should be Row-major 
    // return n-by-n identity matrix I
    public static double[][] Midentity(int n) {
        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++)
            a[i][i] = 1;
        return a;
    }

    // return x^T y
    public static double Mdot(double[] x, double[] y) {
        if (x.length != y.length) throw new RuntimeException("Illegal vector dimensions.");
        double sum = 0.0;
        for (int i = 0; i < x.length; i++)
            sum += x[i] * y[i];
        return sum;
    }

    // return B = A^T
    public static double[][] Mtranspose(double[][] a) {
        int m = a.length;
        int n = a[0].length;
        double[][] b = new double[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                b[j][i] = a[i][j];
        return b;
    }

    // return c = a + b
    public static double[][] Madd(double[][] a, double[][] b) {
        int m = a.length;
        int n = a[0].length;
        double[][] c = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                c[i][j] = a[i][j] + b[i][j];
        return c;
    }

    // return c = a - b
    public static double[][] Msubtract(double[][] a, double[][] b) {
        int m = a.length;
        int n = a[0].length;
        double[][] c = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                c[i][j] = a[i][j] - b[i][j];
        return c;
    }

    // return c = a * b
    public static double[][] Mmultiply(double[][] a, double[][] b) {
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        double[][] c = new double[m1][n2];
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++)
                for (int k = 0; k < n1; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }

    // matrix-vector multiplication (y = A * x)
    public static double[] Mmultiply(double[][] a, double[] x) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                y[i] += a[i][j] * x[j];
        return y;
    }


    // vector-matrix multiplication (y = x^T A)
    public static double[] Mmultiply(double[] x, double[][] a) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != m) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[n];
        for (int j = 0; j < n; j++)
            for (int i = 0; i < m; i++)
                y[j] += a[i][j] * x[i];
        return y;
    }
    
    public static double[][] Minvert(double a[][]) {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i = 0; i < n; ++i) {
            b[i][i] = 1;
        }

        // Transform the matrix into an upper triangle
        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i = 0; i < n - 1; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = 0; k < n; ++k) {
                    b[index[j]][k]
                            -= a[index[j]][i] * b[index[i]][k];
                }
            }
        }

        // Perform backward substitutions
        for (int i = 0; i < n; ++i) {
            x[n - 1][i] = b[index[n - 1]][i] / a[index[n - 1]][n - 1];
            for (int j = n - 2; j >= 0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k = j + 1; k < n; ++k) {
                    x[j][i] -= a[index[j]][k] * x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

// Method to carry out the partial-pivoting Gaussian
// elimination.  Here index[] stores pivoting order.
    public static void gaussian(double a[][], int index[]) {
        int n = index.length;
        double c[] = new double[n];

        // Initialize the index
        for (int i = 0; i < n; ++i) {
            index[i] = i;
        }

        // Find the rescaling factors, one from each row
        for (int i = 0; i < n; ++i) {
            double c1 = 0;
            for (int j = 0; j < n; ++j) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) {
                    c1 = c0;
                }
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j = 0; j < n - 1; ++j) {
            double pi1 = 0;
            for (int i = j; i < n; ++i) {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i = j + 1; i < n; ++i) {
                double pj = a[index[i]][j] / a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l = j + 1; l < n; ++l) {
                    a[index[i]][l] -= pj * a[index[j]][l];
                }
            }
        }
    }

    
    public static double[][] createTranslationMatix(double x,double y,double z){
        double [][] ret = new double[][]
        { {1,0,0,0},
          {0,1,0,0},
          {0,0,1,0},
          {x,y,z,1}
        };
        return ret;
    }
    
        public static double[][] createScaleMatix(double x,double y,double z){
        double [][] ret = new double[][]
        { {x,0,0,0},
          {0,y,0,0},
          {0,0,z,0},
          {0,0,0,1}
        };
        return ret;
    }
    
    public static double[][] createRotXMatix(double angle)
    {
        double [][] ret = new double[][]
        { {1,0,0,0},
          {0, Math.cos(angle) , Math.sin(angle),0},
          {0, -Math.sin(angle), Math.cos(angle),0},
          {0,0,0,1}
        };
        return ret;
    }
    
    public static double[][] createRotYMatix(double angle)
    {
        double [][] ret = new double[][]
        { {Math.cos(angle) ,0,-Math.sin(angle),0},
          {0, 1 , 0 , 0},
          {Math.sin(angle), 0, Math.cos(angle),0},
          {0,0,0,1}
        };
        return ret;
    }
    
    public static double[][] createRotZMatix(double angle)
    {
        double [][] ret = new double[][]
        { {Math.cos(angle) ,Math.sin(angle),0,0},
          {- Math.sin(angle), Math.cos(angle) , 0 , 0},
          {0, 0, 0,0},
          {0,0,0,1}
        };
        return ret;
    }
    
    /**
     * It inverts and then tranposes input matrix
     * @param matrix matrix that is used for points/vectors transform
     * @return matrix that is used for normal transforms
     */
    public static double[][] createNormalTransofrmMatrix(double[][] matrix){
         double [][] ret = Minvert(matrix);
         ret = Mtranspose(ret);
         return ret;
     }
    
    /**
     * 
     * @param tangent x-axis
     * @param normal y-axis
     * @param bitangent z-axis
     * @return 
     */
    public static double[][] createOrientationMatix(Vector3 tangent, Vector3 normal, Vector3 bitangent)
    {
        double [][] ret = new double[][]
        { {tangent.x,   tangent.y,   tangent.z ,0},
          {normal.x,    normal.y ,   normal.z ,0},
          {bitangent.x, bitangent.y, bitangent.z,0},
          {0,0,0,1}
        };
        return ret;
    }

    public static Vector3 sphericalAngleToSphericalV3(double theta, double phi){
        return new Vector3(
            Math.cos(phi) * Math.sin(theta),
            Math.sin(phi) * Math.sin(theta),
            Math.cos(theta));
    }
     
    public static Vector3 sphericalV3ToWorldV3(Vector3 spv){
        return spv.multiplyByM4(createOrientationMatix(
                new Vector3(1,0,0),
                new Vector3(0,0,1),
                new Vector3(0,1,0)
        ));
    }
    
    //we need to multiply it by inverze to OrientationMatrix form sphericalV3ToWorldV3, which is same matrix
    public static Vector3 WorldV3toSphericalV3(Vector3 w){
        return sphericalV3ToWorldV3(w);
    }
    
    /**
     * 
     * @param cv3
     * @return theta, phi
     */
    public static double[] WorldV3toSphericalAngle(Vector3 cv3){
        double r[] = new double[2];
        r[0] = Math.acos(cv3.z);
        r[1] = Math.atan2(cv3.y, cv3.x);
        return r;
    }
    
    
    
    public static class Vector3 {
        public double x, y, z;

        public Vector3(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public Vector3(double[] v){
            this.x = v[0];
            this.y = v[1];
            this.z = v[2];
        }
        
        public Vector3(Vector3 v){
            this.x = v.x;
            this.y = v.y;
            this.z = v.z;
        }
        
        
        public Vector3 toWorldVector3(){
            return sphericalV3ToWorldV3(this);
        }
        
        /**
         * Creates spherical vector from polar and azimuth angles, using left-handed Z-up convention
         * See https://www.scratchapixel.com/lessons/mathematics-physics-for-computer-graphics/geometry/spherical-coordinates-and-trigonometric-functions
         * @param theta θ in radians - vertical angle 
         * @param phi ϕ in radians - horizontal angle 
         */
        public Vector3(double theta, double phi){
            Vector3 r = sphericalAngleToSphericalV3(theta,phi);
            this.x = r.x;
            this.y = r.y;
            this.z = r.z;
        }
        
        public double[] V3toM(){
            return new double[]{x,y,z};
        }
        
        public double[] V3toM4(double fourth){
            return new double[]{x,y,z,fourth};
        }

        /**
         * Adding two vector (or vector and point) gives point
         * @param other
         * @return 
         */
        public Vector3 add(Vector3 other) {
            return new Vector3(x + other.x, y + other.y, z + other.z);
        }
        
        /**
         * Subtracting two points gives vector
         * @param other
         * @return 
         */
        public Vector3 sub(Vector3 other) {
            return new Vector3(x - other.x, y - other.y, z - other.z);
        }

        /**
         * Useful to obtain point from vector and length
         * @param f
         * @return 
         */
        public Vector3 scale(double f) {
            return new Vector3(x * f, y * f, z * f);
        }
        
        /**
         * Orthogonal vector to plain created by "this" and "other"
         * @param other
         * @return perpendicular vector to "this" and "other"
         */
        public Vector3 cross(Vector3 other) {
            /*if(vithinError( Math.abs(this.dot(other)), 1 ,epsilon))
            {
                System.out.println("Cross product error");
            }*/
            
            return new Vector3(y * other.z - z * other.y,
                               z * other.x - x * other.z,
                               x * other.y - y * other.x);
        }

        /**
         * Projections of "this" into "other". In other words, how much is "this" in same direction as "other"
         * @param other
         * @return 
         */
        public double dot(Vector3 other) {
            return x * other.x + y * other.y + z * other.z;
        }
        
        //same as "norm" or "magnitude"
        public double length(){
            return sqrt((x * x) + (y * y) + (z * z));
        }
        
        public Vector3 normalize(){
            double l = length();
            if(l >0)
            {
                //dont know if java would optimize this, so i have 1 div and 3 mutl(should be faster) rather than 3 divs
                double inv_l = 1.0/l;
                return new Vector3(x*inv_l, y*inv_l, z*inv_l);
            }
            return this;
        }
      
        //shorter than chaining
        /**
         * Useful if "this" and "other" are points
         * @param other point
         * @return distance between those two points
         */
        public double distance(Vector3 other){
            return sqrt( (x - other.x)*(x - other.x) + 
                         (y - other.y)*(y - other.y) +
                         (z - other.z)*(z - other.z) );
        }
        
        //shorter than chaining
        /**
         * his uses local instances of normalized "this" and "other" vectors and is calculated as acos betwen two normalized vectors. 
         * @param other
         * @return angle in radians between this vector and other vector
         */
        public double angle(Vector3 other){
            return Math.acos(this.normalize().dot(other.normalize()));
        }
        
        public Vector3 multiplyByM3(double[][] m){
            double[] res = Mmultiply(new double[]{x,y,z}, m);
            return new Vector3(res[0], res[1], res[2]);
        }
        
        public Vector3 multiplyByM4(double[][] m){
            double[] res = Mmultiply(new double[]{x,y,z,1}, m);
            if(res[3] == 0){res[3] = 1;}//to avoid dividing by 0
            return new Vector3(res[0]/res[3], res[1]/res[3], res[2]/res[3]);
        }
        
        
        
        /**
         * Normalize before use
         * @return 
         */
        public double sphericalTheta(){
            return Math.acos(clamp(z,-1,1));
        }
        
        /**
         * Normalize before use
         * @return 
         */
        public double sphericalPhi(){
            double p = Math.atan2(y,x);
            return (p < 0) ? p + 2* Math.PI : p;
        }
    }
    
    public static double clamp(double val, double min, double max){
        return Math.max(min, Math.min(max, val));
    }

    
     /**
     * For Coordinate system info, look at Rp.odt -> 6.Renderer
     * @param A is angle in Radians in XY plain starting form positive X axis going counter clockwise ( X Y -X -Y)
     * @param B is angle in Radians ZY plain starting from negative Z axis going counter clockwise (-Z Y  Z -Y)
     * @return normalized Vector3 as direction set by A and B angles
     */
    public static Vector3 anglesToVector3(double A, double B){
        double x =  Math.cos(A); //add B component
        double y =  Math.sin(A) * Math.sin(B); 
        double z = -Math.cos(B); //add A component
        return new Vector3(x,y,z).normalize();
        
        /*
        //https://en.wikipedia.org/wiki/Spherical_coordinate_system
        //φ is A
        //θ is 
        //B = B + Math.toRadians(180);
        double x =  Math.sin(B)*Math.cos(A);
        double y =  Math.sin(B) * Math.sin(A); 
        double z =  -Math.cos(B); 
        return new Vector3(x,y,z).normalize();*/
    }
    
    public static void printVector3(Vector3 a){
        System.out.println("("+a.x+", "+a.y+", "+a.z+")");
    }
    
    /**
     * http://www.physicsinsights.org/snells_law_1.html
     * @param fromN refraction index on entering side 
     * @param toN refraction index on exiting side
     * @param fromAngle angle of entering beam (radians)
     * @param fromLambda wavelength of exiting beam (nm)
     * @return toAngle (radians),toLambda (nm)
     */
    public static Pair<Double,Double> refract(double fromN, double toN, double fromAngle, double fromLambda){        
        double toLambda = (fromN/toN)*fromLambda;
        double toAngle = Math.asin((fromN/toN)*Math.sin(fromAngle));
        
        return new Pair<Double,Double>(toAngle,toLambda);
    }
    
    /**
     * https://www.scratchapixel.com/lessons/3d-basic-rendering/introduction-to-shading/reflection-refraction-fresnel
     * @param I Incident - entering
     * @param N Normal
     * @return I - 2 * dotProduct(I, N) * N
     */
    public static Vector3 reflect(Vector3 I, Vector3 N){
        return I.sub( N.scale(2*I.dot(N)) );
    }
    
    /**
     * https://www.scratchapixel.com/lessons/3d-basic-rendering/introduction-to-shading/reflection-refraction-fresnel
     * @param I Incident - entering direction
     * @param N Normal
     * @param fromN refraction index of medium we are exiting
     * @param toN refraction index of medium we are entering
     * @param fromLambda wavelength in medium we are exiting
     * @return new direction as first(), new wavelength as second(), if first() == (0,0,0) total internal reflection occured
     */
    public static Pair<Vector3,Double> refract(Vector3 I, Vector3 N, double fromN, double toN, double fromLambda){
        double toLambda = (fromN/toN)*fromLambda;
        
        double etai = fromLambda, etat = toLambda;
        double cosi = clamp(-1,1, I.dot(N));
        Vector3 n = new Vector3(N);
        if(cosi < 0){
            cosi = -cosi;
        }
        else{ 
            //std::swap(etai, etat);
            double tmp = etai;
            etai = etat;
            etat = tmp;
            n = N.scale(-1);
        }
        double eta = etai / etat;
        double k = 1 - eta * eta * (1 - cosi * cosi);
        Vector3 ref = (I.scale(eta)).add( n.scale( eta * cosi - Math.sqrt(k) ) );
        //return k < 0 ? 0 : eta * I + (eta * cosi - sqrtf(k)) * n;
        return new Pair<Vector3, Double>(k < 0 ? new Vector3(0,0,0) : ref, toLambda);
    }
    
}
