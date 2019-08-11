
package logic;

import static logic.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Vector{

    public final double[] v;
    
    public Vector(){
        v = new double[]{R.nextDouble()*2-1, R.nextDouble()*2-1};
        double len = Math.sqrt(v[0]*v[0] + v[1]*v[1]);
        v[0] /= len;
        v[1] /= len;
    }
    
}
