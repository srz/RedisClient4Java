package org.elk.redis4j.test.database.junit;

import org.elk.redis4j.test.database.junit.commands.Connection;
import org.elk.redis4j.test.database.junit.commands.Hashes;
import org.elk.redis4j.test.database.junit.commands.Keys;
import org.elk.redis4j.test.database.junit.commands.Lists;
import org.elk.redis4j.test.database.junit.commands.Server;
import org.elk.redis4j.test.database.junit.commands.Sets;
import org.elk.redis4j.test.database.junit.commands.SortedSets;
import org.elk.redis4j.test.database.junit.commands.Strings;
import org.elk.redis4j.test.database.junit.commands.Transactions;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({Connection.class, Hashes.class, Keys.class, Lists.class, Server.class, Sets.class, SortedSets.class, Strings.class, Transactions.class})
public class AllTests
{
}
