package org.elk.redis4j.test.database.junit.commands;

import java.util.ArrayList;
import java.util.List;

import org.elk.redis4j.test.database.junit.RedisCommandTestBase;
import org.junit.Test;


public class SortedSets extends RedisCommandTestBase
{
	@Test
	public void zadd()
	{
		boolean status = client.sortedSetsAdd("foo", 1, "a");
		assertEquals(true, status);

		status = client.sortedSetsAdd("foo", 10, "b");
		assertEquals(true, status);

		status = client.sortedSetsAdd("foo", 2, "a");
		assertEquals(false, status);
	}

	@Test
	public void zcard()
	{
		client.sortedSetsAdd("foo", 2, "a");
		client.sortedSetsAdd("foo", 10, "b");
		client.sortedSetsAdd("foo", 1, "c");
		client.sortedSetsAdd("foo", 3, "a");

		int size = client.sortedSetsCard("foo");
		assertEquals(3, size);
	}

	@Test
	public void zcount()
	{
		client.sortedSetsAdd("foo", 2, "a");
		client.sortedSetsAdd("foo", 10, "b");
		client.sortedSetsAdd("foo", 1, "c");
		client.sortedSetsAdd("foo", 3, "a");

		Integer result = client.sortedSetsCount("foo", 3, 10);

		assertEquals(2, result.intValue());
	}

	@Test
	public void zincrby()
	{
		client.sortedSetsAdd("foo", 1, "a");
		client.sortedSetsAdd("foo", 2, "b");

		double score = client.sortedSetsIncrementByValue("foo", 2, "a");
		assertEquals(3d, score, 0);

		List<String> expected = new ArrayList<String>(2);
		expected.add("b");
		expected.add("a");
		assertEquals(expected, client.sortedSetsRange("foo", 0, 100));
	}

	@Test
	public void zinterstore()
	{
		client.sortedSetsAdd("foo", 1, "a");
		client.sortedSetsAdd("foo", 2, "b");
		client.sortedSetsAdd("bar", 2, "a");

		Integer result = client.sortedSetsInterStore("dst", "foo", "bar");

		assertEquals(1, result.intValue());

		List<String> expected = new ArrayList<String>(1);
		expected.add("a");

		assertEquals(expected, client.sortedSetsRangeByScore("dst", 0, 100));
	}

	@Test
	public void zrange()
	{
		client.sortedSetsAdd("foo", 2, "a");
		client.sortedSetsAdd("foo", 10, "b");
		client.sortedSetsAdd("foo", 1, "c");
		client.sortedSetsAdd("foo", 11, "a");

		List<String> expected = new ArrayList<String>(2);
		expected.add("c");
		expected.add("b");

		List<String> range = client.sortedSetsRange("foo", 0, 1);
		assertEquals(expected, range);

		expected = new ArrayList<String>(3);
		expected.add("c");
		expected.add("b");
		expected.add("a");

		range = client.sortedSetsRange("foo", 0, 100);
		assertEquals(expected, range);
	}

	@Test
	public void zrangebyscore()
	{
		client.sortedSetsAdd("foo", 2, "a");
		client.sortedSetsAdd("foo", 10, "b");
		client.sortedSetsAdd("foo", 1, "c");
		client.sortedSetsAdd("foo", 3, "a");

		List<String> expected = new ArrayList<String>(2);
		expected.add("c");
		expected.add("a");

		List<String> range = client.sortedSetsRangeByScore("foo", 1, 3);
		assertEquals(expected, range);

		expected = new ArrayList<String>(3);
		expected.add("c");
		expected.add("a");
		expected.add("b");

		range = client.sortedSetsRangeByScore("foo", 0, 100);
		assertEquals(expected, range);
	}

	@Test
	public void zrank()
	{
		client.sortedSetsAdd("foo", 1, "a");
		client.sortedSetsAdd("foo", 2, "b");

		int rank = client.sortedSetsRank("foo", "a");
		assertEquals(0, rank);

		rank = client.sortedSetsRank("foo", "b");
		assertEquals(1, rank);

		assertNull(client.sortedSetsRank("car", "b"));
	}

	@Test
	public void zrem()
	{
		client.sortedSetsAdd("foo", 1, "a");
		client.sortedSetsAdd("foo", 2, "b");

		Boolean status = client.sortedSetsRemove("foo", "a");
		assertEquals(true, status);

		List<String> expected = new ArrayList<String>(1);
		expected.add("b");

		assertEquals(expected, client.sortedSetsRange("foo", 0, 100));

		status = client.sortedSetsRemove("foo", "bar");

		assertEquals(false, status);
	}

	@Test
	public void zremrangeByRank()
	{
		client.sortedSetsAdd("foo", 2, "a");
		client.sortedSetsAdd("foo", 10, "b");
		client.sortedSetsAdd("foo", 1, "c");
		client.sortedSetsAdd("foo", 3, "a");

		Integer result = client.sortedSetsRemoveRangeByRank("foo", 0, 0);

		assertEquals(1, result.intValue());

		List<String> expected = new ArrayList<String>(2);
		expected.add("a");
		expected.add("b");

		assertEquals(expected, client.sortedSetsRange("foo", 0, 100));
	}

	@Test
	public void zremrangeByScore()
	{
		client.sortedSetsAdd("foo", 2, "a");
		client.sortedSetsAdd("foo", 10, "b");
		client.sortedSetsAdd("foo", 1, "c");
		client.sortedSetsAdd("foo", 3, "a");

		Integer result = client.sortedSetsRemoveRangeByScore("foo", -2, -1);

		assertEquals(0, result.intValue());

		result = client.sortedSetsRemoveRangeByScore("foo1", 0, 10);

		assertEquals(0, result.intValue());

		result = client.sortedSetsRemoveRangeByScore("foo", 0, 2);

		assertEquals(1, result.intValue());

		List<String> expected = new ArrayList<String>(2);
		expected.add("a");
		expected.add("b");

		assertEquals(expected, client.sortedSetsRange("foo", 0, 100));
	}

	@Test
	public void zrevrange()
	{
		client.sortedSetsAdd("foo", 2, "a");
		client.sortedSetsAdd("foo", 10, "b");
		client.sortedSetsAdd("foo", 1, "c");
		client.sortedSetsAdd("foo", 11, "a");

		List<String> expected = new ArrayList<String>(2);
		expected.add("a");
		expected.add("b");

		List<String> range = client.sortedSetsRevRange("foo", 0, 1);
		assertEquals(expected, range);

		expected = new ArrayList<String>(3);
		expected.add("a");
		expected.add("b");
		expected.add("c");
		range = client.sortedSetsRevRange("foo", 0, 100);
		assertEquals(expected, range);
	}

	@Test
	public void zrevrangeByScores()
	{
		client.sortedSetsAdd("foo", 2, "a");
		client.sortedSetsAdd("foo", 10, "b");
		client.sortedSetsAdd("foo", 1, "c");
		client.sortedSetsAdd("foo", 3, "a");

		List<String> expected = new ArrayList<String>(2);
		expected.add("a");
		expected.add("c");

		List<String> range = client.sortedSetsRevRangeByScore("foo", 3, 0);
		assertEquals(expected, range);

		expected = new ArrayList<String>(3);
		expected.add("b");
		expected.add("a");
		expected.add("c");

		range = client.sortedSetsRevRangeByScore("foo", 100, 0);
		assertEquals(expected, range);
	}

	@Test
	public void zrevrank()
	{
		client.sortedSetsAdd("foo", 1, "a");
		client.sortedSetsAdd("foo", 2, "b");

		int rank = client.sortedSetsRevRank("foo", "a");
		assertEquals(1, rank);

		rank = client.sortedSetsRevRank("foo", "b");
		assertEquals(0, rank);
	}

	@Test
	public void zscore()
	{
		client.sortedSetsAdd("foo", 2, "a");
		client.sortedSetsAdd("foo", 10, "b");
		client.sortedSetsAdd("foo", 1, "c");
		client.sortedSetsAdd("foo", 3, "a");

		Integer score = client.sortedSetsScore("foo", "b");
		assertEquals(10, score.intValue());

		score = client.sortedSetsScore("foo", "c");
		assertEquals(1, score.intValue());

		score = client.sortedSetsScore("foo", "s");
		assertNull(score);
	}

	@Test
	public void zunionstore()
	{
		client.sortedSetsAdd("foo", 1, "a");
		client.sortedSetsAdd("foo", 2, "b");
		client.sortedSetsAdd("bar", 2, "a");
		client.sortedSetsAdd("bar", 2, "b");

		Integer result = client.sortedSetsUnionStore("dst", "foo", "bar");

		assertEquals(2, result.intValue());

		List<String> expected = new ArrayList<String>(2);
		expected.add("a");
		expected.add("b");

		assertEquals(expected, client.sortedSetsRangeByScore("dst", 0, 100));
	}
}