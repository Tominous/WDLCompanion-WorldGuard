package wdl.worldguard;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import wdl.range.IRangeGroup;
import wdl.range.IRangeGroupType;

public class WorldGuardRangeGroupType implements
		IRangeGroupType<WorldGuardRangeProducer> {

	@Override
	public boolean isValidConfig(ConfigurationSection config,
			List<String> warnings, List<String> errors) {
		boolean isValidConfig = true;
		
		if (config.isSet("preservePolygons")) {
			if (!config.isBoolean("preservePolygons")) {
				errors.add("'preservePolygons' (use exact edges on non-cubic "
						+ " regions) must be a Boolean!");
				
				isValidConfig = false;
			}
		}
		if (!config.isSet("ownershipType")) {
			warnings.add("'ownershipType' is not specified!  The default, " +
					"any (either owner or member), will be used.");
		} else if (!config.isString("ownershipType")
				|| !(OwnershipType.NAMES.contains(config
						.getString("ownershipType")))) {
			errors.add("'ownershipType' must be a String with one of these " +
					"values: " + OwnershipType.NAMES + ".  (Currently set " + 
					"to '" + config.getString("ownershipType") + "')");
			
			isValidConfig = false;
		}
		
		return isValidConfig;
	}

	@Override
	public WorldGuardRangeProducer createRangeProducer(IRangeGroup group,
			ConfigurationSection config) {
		return new WorldGuardRangeProducer(group, 
				OwnershipType.match(config.getString("ownershipType", "any")),
				config.getBoolean("preservePolygons", false));
	}

	@Override
	public void dispose() { }

}
