package com.microwise.smartservices.strategy;

import com.microwise.smartservices.netconn.form.MessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.microwise.smartservices.trilateration.TrilaterationFunction;
import com.microwise.smartservices.trilateration.NonLinearLeastSquaresSolver;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer.Optimum;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

/**
 * Created by lee on 5/23/2017.
 */
@Component("trilateration")
public class TrilaterationStrategy implements IStrategy {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private double[][] anchors;

    @Override
    public MessageBean processMessage(MessageBean old) {
        logger.debug("" + old);
        MessageBean newBean = null;
        if ("setAnchors".equals(old.getContentBean().getCommand())) {
            setAnchors(old);
        } else if ("setDistances".equals(old.getContentBean().getCommand())) {
            double[] distances = getDistancesByMessageBean(old);
            double[] target = calcCoords(anchors, distances);
            newBean = buildCoordsInfo(old, target);
            logger.debug("" + newBean);
        }
        return newBean;
    }

    private MessageBean buildCoordsInfo(MessageBean old, double[] target) {
        old.getContentBean().setCommand("placePlayer");
        Object[] args = new Object[target.length];
        for(int i=0; i<target.length; i++){
            args[i] = target[i];
        }
        old.getContentBean().setArgs(args);
        return old;
    }

    private double[] calcCoords(double[][] anchors, double[] distances) {
        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(
                new TrilaterationFunction(anchors, distances), new LevenbergMarquardtOptimizer());
        Optimum optimum = solver.solve();

        double[] target = optimum.getPoint().toArray();
        return target;
    }

    private double[] getDistancesByMessageBean(MessageBean old) {
        Object[] args = old.getContentBean().getArgs();
        double[] distances = new double[args.length];
        for(int i=0; i<distances.length; i++){
            distances[i] = Double.valueOf(args[i].toString());
        }
        return distances;
    }

    private void setAnchors(MessageBean old) {
        Object[] args = old.getContentBean().getArgs();
        anchors = new double[args.length][3];
        for (int i = 0; i < args.length; i++) {
            String arg = args[i].toString();
            arg = arg.substring(1, arg.length()-1);
            String[] vector = arg.split(",");
            for(int j=0; j<anchors[i].length; j++){
                anchors[i][j] = Double.valueOf(vector[j]);
            }
        }
    }
}
