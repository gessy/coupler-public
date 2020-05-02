package com.offgrid.coupler.data;

import com.offgrid.coupler.data.entity.Region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionPreset {

    private static int MIN_ZOOM = 5;
    private static int MAX_ZOOM = 13;
    private static int TARGET_ZOOM = 11;

    public static Map<String, List<Region>> get() {
        Map<String, List<Region>> preset = new HashMap<>();

        preset.put("Russia", getRussia());
        preset.put("Estonia", getEstonia());
        preset.put("Germany", getGermany());
        preset.put("Italy", getItaly());
        preset.put("France", getFrance());

        return preset;
    }

    private static List<Region> getRussia() {
        List<Region> regions = new ArrayList<>();

        regions.add(new Region("Saint Petersburg", null, 60.253601d, 30.785961d, 59.625175d, 29.384460d, 59.930584d, 30.349898d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Vologda", null, 59.281179d, 40.033205d, 59.165494d, 39.727304d, 59.221759d, 39.892443d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Kostroma", null, 57.844401d, 41.061456d, 57.705908d, 40.806711d, 57.768192d, 40.943382d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Novosibirsk", null, 55.145691d, 83.159513d, 54.790562d, 82.729673d, 54.982358d, 82.897416d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Tomsk", null, 56.584865d, 85.125355d, 56.394271d, 84.887759d, 56.488957d, 84.955294d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Yekaterinburg", null, 56.970831d, 60.847402d, 56.649608d, 60.379465d, 56.820072d, 60.610509d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Yaroslavl", null, 57.784036d, 40.011454d, 57.521894d, 39.709370d, 57.625535d, 39.883903d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Kazan", null, 55.923795d, 49.318112d, 55.658151d, 48.821398d, 55.786234d, 49.118806d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Samara", null, 53.432986d, 50.398572d, 53.082848d, 49.987967d, 53.199888d, 50.102407d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        return regions;
    }

    private static List<Region> getEstonia() {
        List<Region> regions = new ArrayList<>();

        regions.add(new Region("Tallinn", null, 59.502995d, 24.930497d, 59.349180d, 24.549786d, 59.418211d, 24.743730d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Tartu", null, 58.413400d, 26.801452d, 58.336059d, 26.667080d, 58.367205d, 26.729455d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        return regions;
    }

    private static List<Region> getGermany() {
        List<Region> regions = new ArrayList<>();

        regions.add(new Region("Frankfurt", null, 50.234139d, 8.807532d, 50.011570d, 8.461531d, 50.110696d, 8.685237d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Bremen", null, 53.237812d, 9.007685d, 53.010679d, 8.472598d, 53.072774d, 8.807936d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Hanover", null, 52.455063d, 9.921061d, 52.302231d, 9.599596d, 52.374909d, 9.732248d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Dresden", null, 51.181463d, 13.967587d, 50.970492d, 13.575631d, 51.062511d, 13.781506d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Nuremberg", null, 49.543949d, 11.214742d, 49.328651d, 10.980387d, 49.440270d, 11.082108d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Stuttgart", null, 48.872576d, 9.318067d, 48.688051d, 9.032803d, 48.775650d, 9.171253d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        return regions;
    }

    private static List<Region> getItaly() {
        List<Region> regions = new ArrayList<>();

        regions.add(new Region("Bari", null, 41.232581d, 17.391595d, 40.689765d, 16.194127d, 41.110991d, 16.866817d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Florence", null, 44.243225d, 11.768970d, 43.445415d, 10.700848d, 43.782108d, 11.247956d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Bologna", null, 44.816285d, 11.844709d, 44.060657d, 10.789684d, 44.503000d, 11.343413d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Milan", null, 45.651902d, 9.546299d, 45.269224d, 8.698749d, 45.472584d, 9.184940d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Genoa", null, 44.670273d, 9.585430d, 44.243500d, 8.560071d, 44.410180d, 8.918640d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Padua", null, 45.695822d, 12.209058d, 45.098947d, 11.384630d, 45.409150d, 11.879915d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        return regions;
    }

    private static List<Region> getFrance() {
        List<Region> regions = new ArrayList<>();

        regions.add(new Region("Paris", null, 48.905774d, 2.418303d, 48.811924d, 2.220616d, 48.860408d, 2.335198d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Lyon", null, 45.809546d, 4.901210d, 45.705493d, 4.769751d, 45.753399d, 4.835636d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        regions.add(new Region("Toulouse", null, 43.670252d, 1.516610d, 43.534401d, 1.347519d, 43.594758d, 1.432858d, MIN_ZOOM, MAX_ZOOM, TARGET_ZOOM));
        return regions;
    }

}
