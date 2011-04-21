package org.elk.redis4j.test.cache.junit;

import org.elk.redis4j.test.cache.junit.commands.Hashes;
import org.elk.redis4j.test.cache.junit.commands.Keys;
import org.elk.redis4j.test.cache.junit.commands.Lists;
import org.elk.redis4j.test.cache.junit.commands.Sets;
import org.elk.redis4j.test.cache.junit.commands.SortedSets;
import org.elk.redis4j.test.cache.junit.commands.Strings;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;



@RunWith(Suite.class)
@Suite.SuiteClasses({Hashes.class, Keys.class, Lists.class, Sets.class, SortedSets.class, Strings.class})
public class AllTests
{
}
