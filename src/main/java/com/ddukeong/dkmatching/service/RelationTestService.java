package com.ddukeong.dkmatching.service;

import com.ddukeong.dkmatching.model.Many;
import com.ddukeong.dkmatching.model.One;
import com.ddukeong.dkmatching.repository.ManyRepository;
import com.ddukeong.dkmatching.repository.OneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RelationTestService {

    private final ManyRepository manyRepository;

    private final OneRepository oneRepository;

    public RelationTestService(ManyRepository manyRepository, OneRepository oneRepository) {
        this.manyRepository = manyRepository;
        this.oneRepository = oneRepository;
    }

    public void saveMany(Many many) {
        manyRepository.save(many);
    }

    public List<Many> getManys() {
        return manyRepository.findAll();
    }

    public Many getMany(Long seq) {
        return manyRepository.findById(seq).orElse(null);
    }

    public void deleteMany(Many many) {
        /**
         * FK 제약 조건 때문에 삭제 전에 관계를 끊어줘야함
         * 관계의 주인이 Many가 아니라 One 임으로 이런방식으로 끊어줘야함
         *
         * many.getOnes(null)은 의미 없음
         */
        many.getOnes().forEach(one -> one.setMany(null));
        manyRepository.delete(many);
    }

    public void deleteAllMany() {
        getManys().forEach(this::deleteMany);
    }

    public void saveOne(One one) {
        oneRepository.save(one);
    }

    public List<One> getOnes() {
        return oneRepository.findAll();
    }

    public One getOne(Long seq) {
        return oneRepository.findById(seq).orElse(null);
    }

    public void deleteAllOne() {
        oneRepository.deleteAll();
    }

}
