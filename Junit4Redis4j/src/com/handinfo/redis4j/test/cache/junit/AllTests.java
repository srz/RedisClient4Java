package com.handinfo.redis4j.test.cache.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.handinfo.redis4j.test.cache.junit.commands.Hashes;
import com.handinfo.redis4j.test.cache.junit.commands.Keys;
import com.handinfo.redis4j.test.cache.junit.commands.Lists;
import com.handinfo.redis4j.test.cache.junit.commands.Sets;
import com.handinfo.redis4j.test.cache.junit.commands.SortedSets;
import com.handinfo.redis4j.test.cache.junit.commands.Strings;


@RunWith(Suite.class)
@Suite.SuiteClasses({Hashes.class, Keys.class, Lists.class, Sets.class, SortedSets.class, Strings.class})
public class AllTests
{
}
