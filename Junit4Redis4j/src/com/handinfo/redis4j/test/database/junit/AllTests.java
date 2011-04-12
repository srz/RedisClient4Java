package com.handinfo.redis4j.test.database.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.handinfo.redis4j.test.database.junit.commands.Connection;
import com.handinfo.redis4j.test.database.junit.commands.Hashes;
import com.handinfo.redis4j.test.database.junit.commands.Keys;
import com.handinfo.redis4j.test.database.junit.commands.Lists;
import com.handinfo.redis4j.test.database.junit.commands.Server;
import com.handinfo.redis4j.test.database.junit.commands.Sets;
import com.handinfo.redis4j.test.database.junit.commands.SortedSets;
import com.handinfo.redis4j.test.database.junit.commands.Strings;
import com.handinfo.redis4j.test.database.junit.commands.Transactions;

@RunWith(Suite.class)
@Suite.SuiteClasses({Connection.class, Hashes.class, Keys.class, Lists.class, Server.class, Sets.class, SortedSets.class, Strings.class, Transactions.class})
public class AllTests
{
}
