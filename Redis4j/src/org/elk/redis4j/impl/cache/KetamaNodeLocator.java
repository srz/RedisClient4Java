package org.elk.redis4j.impl.cache;

import java.util.SortedMap;
import java.util.TreeMap;

import org.elk.redis4j.api.ISession;


public final class KetamaNodeLocator
{

	private TreeMap<Long, ISession> ketamaNodes;
	private HashAlgorithm hashAlg;
	private int numReps = 160;

	public KetamaNodeLocator(ISession[] sessions, HashAlgorithm alg, int nodeCopies)
	{
		hashAlg = alg;
		ketamaNodes = new TreeMap<Long, ISession>();

		if (nodeCopies > 0)
			numReps = nodeCopies;

		for (ISession session : sessions)
		{
			for (int i = 0; i < numReps / 4; i++)
			{
				byte[] digest = hashAlg.computeMd5(session.getName() + i);
				for (int h = 0; h < 4; h++)
				{
					long m = hashAlg.hash(digest, h);

					ketamaNodes.put(m, session);
				}
			}
		}
	}

	public ISession getPrimary(final String k)
	{
		byte[] digest = hashAlg.computeMd5(k);
		ISession rv = getNodeForKey(hashAlg.hash(digest, 0));
		return rv;
	}

	ISession getNodeForKey(long hash)
	{
		final ISession rv;
		Long key = hash;
		if (!ketamaNodes.containsKey(key))
		{
			SortedMap<Long, ISession> tailMap = ketamaNodes.tailMap(key);
			if (tailMap.isEmpty())
			{
				key = ketamaNodes.firstKey();
			} else
			{
				key = tailMap.firstKey();
			}
			// For JDK1.6 version
			// key = ketamaNodes.ceilingKey(key);
			// if (key == null) {
			// key = ketamaNodes.firstKey();
			// }
		}

		rv = ketamaNodes.get(key);
		return rv;
	}
}
