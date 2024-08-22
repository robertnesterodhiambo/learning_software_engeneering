import java.util.*;

public class Inventory {

    private final Set<CitrusTree> inventory = new HashSet<>();

    public void addItem(CitrusTree citrusTree){
        this.inventory.add(citrusTree);
    }

    public Set<String> getAllTypes(){
        Set<String> allTypes = new LinkedHashSet<>();
        for(CitrusTree citrusTree: inventory){
            allTypes.add(citrusTree.getType());
        }
        return allTypes;
    }

    public List<CitrusTree> findMatch(CitrusTree dreamCitrusTree){
        List<CitrusTree> matching = new ArrayList<>();
        for(CitrusTree citrusTree: inventory){
            if(!citrusTree.getType().equals(dreamCitrusTree.getType())) continue;
            if(!citrusTree.getPotSizeToPriceOptions().containsKey(dreamCitrusTree.getPotSize())) continue;
            if(citrusTree.isDwarf() != dreamCitrusTree.isDwarf()) continue;
            Float correspondingPrice = citrusTree.getPotSizeToPriceOptions().get(dreamCitrusTree.getPotSize());
            if(correspondingPrice < dreamCitrusTree.getMinPrice() || correspondingPrice > dreamCitrusTree.getMaxPrice()) continue;
            matching.add(citrusTree);
        }
        return matching;
    }

}
