package com.orientechnologies.ldbc.snb.benchmark.loader.tasks;

import com.orientechnologies.ldbc.snb.benchmark.loader.DBLoader;
import com.orientechnologies.ldbc.snb.benchmark.loader.dto.PersonLikesPostDTO;
import com.orientechnologies.ldbc.snb.benchmark.loader.schema.Knows;
import com.orientechnologies.ldbc.snb.benchmark.loader.schema.Likes;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class PersonLikesPostLoaderTask extends AbstractLoaderTask<PersonLikesPostDTO> {
  public PersonLikesPostLoaderTask(ArrayBlockingQueue<PersonLikesPostDTO> dataQueue, ODatabasePool pool,
      AtomicLong operationsCounter) {
    super(dataQueue, pool, operationsCounter);
  }

  @Override
  protected void execute(ODatabaseSession session, PersonLikesPostDTO dto) {
    final String query = String
        .format("create edge %s from (select from %s where %s = ?) to (select from %s where %s = ?) set %s = ?", Likes.NAME,
            DBLoader.PERSON_CLASS, DBLoader.PERSON_ID, DBLoader.POST_CLASS, DBLoader.MESSAGE_ID, Knows.CREATION_DATE);
    session.command(query, dto.id, dto.to, dto.creationDate).close();
  }
}
