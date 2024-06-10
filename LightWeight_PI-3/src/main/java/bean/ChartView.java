package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;

@RequestScoped
@ManagedBean(name = "chartView")
public class ChartView implements Serializable {
    private static final long serialVersionUID = 1L;
    private LineChartModel lineModel;

    @PostConstruct
    public void init() {
        createLineModel();
    }

    public void createLineModel() {
        lineModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>(); // Mudando para List<Object>
        values.add(20);
        values.add(24);
        values.add(30);
        values.add(20);
        values.add(10);
        values.add(5);
        values.add(18);
        values.add(22);
        values.add(30);
        values.add(34);
        values.add(40);
        values.add(50);
        values.add(65);
        values.add(63);
        values.add(31);
        values.add(10);
        values.add(1);
        dataSet.setData(values); // Chamando setData com List<Object>
        dataSet.setFill(false);
        dataSet.setLabel("My First Dataset");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setTension(0.1);
        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        labels.add("8:00");
        labels.add("9:00");
        labels.add("10:00");
        labels.add("11:00");
        labels.add("12:00");
        labels.add("13:00");
        labels.add("14:00");
        labels.add("15:00");
        labels.add("16:00");
        labels.add("17:00");
        labels.add("18:00");
        labels.add("19:00");
        labels.add("20:00");
        labels.add("21:00");
        labels.add("22:00");
        labels.add("23:00");
        labels.add("24:00");
        data.setLabels(labels);

        lineModel.setData(data);
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }
}
